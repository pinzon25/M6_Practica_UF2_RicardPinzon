package com.example.demo.app

import com.example.demo.app.ActualitzarAlumne.Companion.al
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import tornadofx.View
import tornadofx.alert
import tornadofx.find
import tornadofx.reloadViewsOnFocus
import java.lang.reflect.Array.getInt
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class ActualitzarAlumne: View() {
    private val message: String by param()
    var conexio: Connection = MenuPrincipal().conexio
    private val botoSortir: Button by fxid("Bt_surtActualitza")
    private val botoActualitzar: Button by fxid("Bt_actualitza")
    val nomalumne: TextField by fxid("Tf_nomAl")
    val cognomalumne: TextField by fxid("Tf_cognomAl")
    val edatalumne: TextField by fxid("Tf_edatAl")

    override val root : AnchorPane by fxml()

//FALTA PODER MODIFICAR ELS VALORS DEL ALUMNE I FER EL UPDATE A LA TAULA DE SQL.

    companion object {
        var al:Alumne? = null
    }

    init{
        println("Estem al menu d'actualitzar dades, l'alumne es: "+message)
            obteAlumne(conexio,message.toInt())
            //println(Companion.al) //AQUI EL OBJECTE al ESTA BUIT/NULL.
            nomalumne.text = Companion.al?.nom
            cognomalumne.text = Companion.al?.cognoms
            edatalumne.text = Companion.al?.edat.toString()

        botoActualitzar.setOnMouseClicked {
           var a:Alumne = Alumne(al!!.id,nomalumne.text,cognomalumne.text,edatalumne.text.toInt())
            println(a)
            actualitza(a,conexio)
            nomalumne.text=null
            cognomalumne.text=null
            edatalumne.text=null
        }

        botoSortir.setOnMouseClicked {
            replaceWith<MenuPrincipal>() }
        }



}

 fun obteAlumne(c:Connection, id: Int){
     //println("Estem al metode obteAlumne")

        val ps = c.prepareStatement("SELECT * FROM Alumne WHERE id_alumne = ?")
        ps.setInt(1, id)
        val rs = ps.executeQuery()

        while (rs.next()) {
            val id = rs.getInt("id_alumne")
            val nom = rs.getString("Nom")
            val cognom = rs.getString("Cognoms")
            val edat = rs.getInt("edat")

            al = Alumne(id, nom, cognom, edat)

       // }
    }

     //println("Ha acabat el metode obteAlumne")
    //c.close()
}

fun actualitza(a:Alumne, c:Connection){
    println("Has entrat al metode on actualitzem els camps al SQL.")

    val ps = c.prepareStatement("UPDATE Alumne SET Nom = ?, Cognoms = ?,  edat = ? WHERE id_alumne = ?")
    ps.setString(1, a.nom)
    ps.setString(2,a.cognoms)
    ps.setInt(3,a.edat)
    ps.setInt(4,a.id)
    val rs = ps.executeUpdate()
}



