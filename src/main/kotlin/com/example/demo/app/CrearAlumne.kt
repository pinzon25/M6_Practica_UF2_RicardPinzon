package com.example.demo.app

import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import tornadofx.View
import tornadofx.find
import tornadofx.reloadViewsOnFocus
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class CrearAlumne: View() {
    private val tf_NomAlumne:TextField by fxid("Tf_Nom")
    private val tf_CognomsAlumne: TextField by fxid("Tf_Cognoms")
    private val tf_EdatAlumne:TextField by fxid("Tf_Edat")
    private val botocrear: Button by fxid("Bt_crear")
    private val botoSortir:Button by fxid("Bt_Sortir")
    private var conexio:Connection = Connexio().connexio()

    override val root : AnchorPane by fxml()



    init{
    botocrear.setOnMouseClicked {
        println("Has clicat el boto per crear un alumne.")

    val alumne = Alumne(0,tf_NomAlumne.getText(),tf_CognomsAlumne.getText(),tf_EdatAlumne.getText().toInt())
        CrearNouAlumne(conexio,alumne)
    }

    botoSortir.setOnMouseClicked {

        replaceWith<MenuPrincipal>() }
    }
}

private fun CrearNouAlumne(c:Connection,alumne:Alumne){

    println("Has entrat al metode CrearNouAlumne.")
    val ps = c.prepareStatement("INSERT INTO Alumne (Nom,Cognoms,Edat) VALUES (?,?,?)")
    ps.setString(1,alumne.nom)
    ps.setString(2,alumne.cognoms)
    ps.setInt(3,alumne.edat)
    ps.executeUpdate()
    //c.close()
}

