package com.apero.kmpdemo.android.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import org.koin.android.ext.android.get
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

abstract class BaseActivity : ComponentActivity() {
    
    protected open fun initializeActivityResults() {
        // Override this in subclasses to register activity results
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        initializeActivityResults() // Call this before super.onCreate()
        super.onCreate(savedInstanceState)
        
        // Create a temporary module to provide this activity instance
        val activityModule = module {
            single<ComponentActivity> { this@BaseActivity }
        }
        
        // Load the module
        loadKoinModules(activityModule)
    }
} 