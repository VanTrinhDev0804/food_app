package com.trytest.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.trytest.foodorder.DataClass.DataOrder
import com.trytest.foodorder.adapter.PlaceYourOrderAdapter
import com.trytest.foodorder.models.Menus
import com.trytest.foodorder.models.RestaurentModel
import kotlinx.android.synthetic.main.activity_place_your_order.*
import java.util.*

class PlaceYourOrderActivity : AppCompatActivity() {

    var placeYourOrderAdapter: PlaceYourOrderAdapter? = null
    var isDeliveryOn: Boolean = false

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_your_order)


        val restaurantModel: RestaurentModel? = intent.getParcelableExtra("RestaurantModel")
        val actionbar: ActionBar? = supportActionBar
        actionbar?.setTitle(restaurantModel?.name)
        actionbar?.setSubtitle(restaurantModel?.address)
        actionbar?.setDisplayHomeAsUpEnabled(true)

        buttonPlaceYourOrder.setOnClickListener {
            onPlaceOrderButtonCLick(restaurantModel)
        }

        initRecyclerView(restaurantModel)
        calculateTotalAmount(restaurantModel)
    }

    private fun initRecyclerView(restaurantModel: RestaurentModel?) {
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        placeYourOrderAdapter = PlaceYourOrderAdapter(restaurantModel?.menus)
        cartItemsRecyclerView.adapter =placeYourOrderAdapter

    }

    private fun calculateTotalAmount(restaurantModel: RestaurentModel?) {
        var subTotalAmount = 0f
        for(menu in restaurantModel?.menus!!) {
            subTotalAmount += menu?.price!!  * menu?.totalInCart!!

        }
        tvSubtotalAmount.text =  String.format("%.2f", subTotalAmount) +"vnđ"
        if(isDeliveryOn) {
            tvDeliveryChargeAmount.text = String.format("%.2f", restaurantModel.delivery_charge?.toFloat())+"vnđ"
            subTotalAmount += restaurantModel?.delivery_charge?.toFloat()!!
        }

        tvTotalAmount.text = String.format("%.2f", subTotalAmount) + "vnđ"
    }

    private fun onPlaceOrderButtonCLick(restaurantModel: RestaurentModel?) {
        if(TextUtils.isEmpty(inputName.text.toString())) {
            inputName.error =  "Nhập tên của bạn"
            return
        } else if(isDeliveryOn && TextUtils.isEmpty(inputAddress.text.toString())) {
            inputAddress.error =  "Nhập địa chỉ của bạn"
            return
        } else if(isDeliveryOn && TextUtils.isEmpty(inputPhone.text.toString())) {
            inputPhone.error =  "Nhập thành số điện thoại"
            return
        }
        var namecurrent:String =  inputName.text.toString()
        var addresscurrent :String = inputAddress.text.toString()
        var phonecurrent :String = inputPhone.text.toString()

        val intent = Intent(this@PlaceYourOrderActivity, SuccessOrderActivity::class.java)
        /// đẩy đơn hàng lên realtime database !!!
        writeOrderOnDataBase(namecurrent, addresscurrent, phonecurrent, restaurantModel?.menus)
        intent.putExtra("RestaurantModel", restaurantModel)
        startActivityForResult(intent, 1000)
    }

    private fun writeOrderOnDataBase(
        nameCurr:String,
        addressCur: String,
        phoneCur: String,
        menus: List<Menus?>?
    ) {
        database = FirebaseDatabase.getInstance().reference
        val order = DataOrder( addressCur, phoneCur , menus )
        val restaurantModel: RestaurentModel? = intent.getParcelableExtra("RestaurantModel")

        restaurantModel?.name?.let {
            database.child("Order").child(it).push().child(nameCurr).setValue(order) }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1000) {
            setResult(RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}