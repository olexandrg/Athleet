package net.azurewebsites.athleet

import net.azurewebsites.athleet.ApiLib.Api

fun apiFactory(): Api {return Api.createSafe()}

fun tokenFactory(): String {
    return tokenMaster.tokenFactory()
}