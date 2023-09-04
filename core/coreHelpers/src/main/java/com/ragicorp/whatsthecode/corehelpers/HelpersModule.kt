package com.ragicorp.whatsthecode.corehelpers

import org.koin.dsl.module

val helpersModule = module {
    single { ActivityProvider() }
    single { PermissionsManager(getActivity()) }
}