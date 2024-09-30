package com.example.todoapplication.presentation.viewmodes

import android.os.Bundle
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DashBoardViewModel @Inject constructor() : WirelessViewModel() {
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
        finish()
    }

    override fun onNotification(id: Any?, arg: Any?) {
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
}
