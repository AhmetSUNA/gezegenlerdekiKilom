package ahmetsuna.com.gezegenlerdekikilom

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.CheckBox
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    val KILO_TO_POUND = 2.2045
    val MARS = 0.38
    val VENUS = 0.91
    val JUPITER = 2.34
    val POUND_TO_KILO = 0.45359237

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Glide.with(this).load(R.drawable.gezegen).into(imageView)

        tvSonuc.text=savedInstanceState?.getString("sonuc")   //ekran çevirince onCreate tekrardan tetikleniyor ve text'teki ifademiz tekrardan text'e atılıyor

        cbVenus.setOnClickListener(this)
        cbMars.setOnClickListener(this)
        cbJupiter.setOnClickListener(this)

        /*btnHesapla.setOnClickListener {

            var kullaniciAgirlikPound = kiloToPound(kullaniciKilo.toString().toDouble())
            var marstakiAgirlikPound  = kullaniciAgirlikPound * MARS
            var marstakiAgirlikKilo   = poundToKilo(marstakiAgirlikPound)

            tvSonuc.text = marstakiAgirlikKilo.formatla(2).toString()
        }*/
    }

    fun kiloToPound(kilo: Double) : Double{

        return kilo * KILO_TO_POUND
    }

    //text'teki sonuç degerini "sonuç" ta tutuyoruz ki ekranı yan çevirince sonuç kaybolmasın
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("sonuc",tvSonuc.text.toString())

    }

    fun poundToKilo(pound : Double) : Double {

        return pound * POUND_TO_KILO
    }

    override fun onClick(v: View?) {

        v as CheckBox
        var isChecked:Boolean = v.isChecked


        if (!TextUtils.isEmpty((etKilo.text.toString()))){
            var kullaniciKilo = etKilo.text.toString().toDouble()
            var kullaniciPound = kiloToPound(kullaniciKilo)

            when(v.id){

                R.id.cbMars -> if(isChecked){
                    cbJupiter.isChecked=false
                    cbVenus.isChecked=false
                    hesaplaAgirlikPound(kullaniciPound, v)
                }
                R.id.cbVenus -> if(isChecked){
                    cbJupiter.isChecked=false
                    cbMars.isChecked=false
                    hesaplaAgirlikPound(kullaniciPound, v)

                }
                R.id.cbJupiter -> if(isChecked){
                    cbMars.isChecked=false
                    cbVenus.isChecked=false
                    hesaplaAgirlikPound(kullaniciPound, v)

                }
            }
        }


    }

    fun hesaplaAgirlikPound(pound:Double, checkBox:CheckBox){

        var sonuc : Double = 0.0

        when (checkBox.id){

            R.id.cbMars -> sonuc = pound * MARS
            R.id.cbJupiter -> sonuc = pound * JUPITER
            R.id.cbVenus -> sonuc = pound * VENUS
            else -> sonuc = 0.0
        }

        var sonucToKilo = poundToKilo(sonuc)
        tvSonuc.text = sonucToKilo.formatla(2)
    }

    //formatla isimli method virgülden sonra kaç basamak olması gerektigini hesaplıyor
    fun Double.formatla(kacTaneRakam:Int) = java.lang.String.format("%.${kacTaneRakam}f",this)
}
