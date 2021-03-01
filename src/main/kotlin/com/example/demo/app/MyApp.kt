package com.example.demo.app

import com.example.demo.view.MainView
import tornadofx.App
import javafx.stage.Stage
import tornadofx.*

class MyApp: App(MenuPrincipal::class, Styles::class)





fun main(args: Array<String>) {
        tornadofx.launch<MyApp>(args)
    }
