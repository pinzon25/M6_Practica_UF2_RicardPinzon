package com.example.demo.app

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Scope
import tornadofx.getProperty

data class Alumne(var id:Int,var nom:String, var cognoms:String, var edat:Int) {

    val idProperty = SimpleIntegerProperty(id)

    val nomProperty = SimpleStringProperty(nom)

    val cognomsProperty = SimpleStringProperty(cognoms)

    val edatProperty = SimpleIntegerProperty(edat)


}


