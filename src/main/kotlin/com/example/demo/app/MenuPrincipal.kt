package com.example.demo.app

import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import tornadofx.View
import tornadofx.*
import java.sql.Connection


class MenuPrincipal: View() {

    private val botoCrearAlumne: Button by fxid("Bt_crearAlumne")
    private val botoEsborrarAlumne: Button by fxid("Bt_EsborrarAlumne")
    private val botoActualitzarAlumne: Button by fxid("Bt_ActualitzarAlumne")
    var conexio: Connection = Connexio().connexio()
    var llistatAlumnes:MutableList<Alumne> = ArrayList()
    var t:javafx.scene.control.TableView<Alumne>? = null
    var al:Alumne? = null
    override val root : AnchorPane by fxml()



    init {
        runAsync {

        carregaAlumnes(conexio, llistatAlumnes)

        val a = FXCollections.observableArrayList(llistatAlumnes)

        with(root) {
          t = tableview(a) {
            column("Id",Alumne::idProperty)
            column("Nom",Alumne::nomProperty)
            column("Cognoms",Alumne::cognomsProperty)
            column("Edat",Alumne::edatProperty)
            isEditable=true
            prefHeight=365.0
            prefWidth= 345.0
            layoutX=306.0
            layoutY=100.0
             }

           }
        }


    botoCrearAlumne.setOnMouseClicked { openInternalWindow<CrearAlumne>() }

        botoActualitzarAlumne.setOnMouseClicked {
             al = t!!.selectedItem
            println(al)


find<ActualitzarAlumne>(params = mapOf("message" to al!!.id.toString()))

            replaceWith<ActualitzarAlumne>() }

        botoEsborrarAlumne.setOnMouseClicked {
            //runAsync {
            var al = t!!.selectedItem!!.id
            esborraAlumne(conexio,al)
           // }
        }
    }
}

private fun carregaAlumnes(c:Connection,alumnes:MutableList<Alumne>) {

        val ps = c.createStatement().executeQuery("SELECT * FROM Alumne")
        while (ps.next()) {
            val id = ps.getInt("id_alumne")
            val nom = ps.getString("Nom")
            val cognom = ps.getString("Cognoms")
            val edat = ps.getInt("edat")

            val al = Alumne(id, nom, cognom, edat)

            alumnes.add(al)
        }

    //c.close()
}

private fun esborraAlumne(c:Connection, id: Int?){
        val l = MenuPrincipal().llistatAlumnes
        val ps = c.prepareStatement("DELETE FROM Alumne WHERE id_alumne = ?")

        if (id != null) {
            ps.setInt(1, id)
            ps.executeUpdate()
            alert(Alert.AlertType.CONFIRMATION, "", "L'alumne s'ha esborrat correctament!")
        } else {
            alert(Alert.AlertType.ERROR, "", "No has escollit ningun alumne!")
        }

 //c.close()
}

