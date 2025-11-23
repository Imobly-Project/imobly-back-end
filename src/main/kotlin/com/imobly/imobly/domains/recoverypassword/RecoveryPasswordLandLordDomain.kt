package com.imobly.imobly.domains.recoverypassword

import com.imobly.imobly.domains.users.LandLordDomain
import java.time.LocalDateTime

class RecoveryPasswordLandLordDomain(
    var id: String? = null,

    var landLord: LandLordDomain,

    var  token: String,

    var moment: LocalDateTime = LocalDateTime.now()
)