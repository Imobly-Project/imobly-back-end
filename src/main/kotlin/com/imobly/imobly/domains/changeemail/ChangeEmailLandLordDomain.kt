package com.imobly.imobly.domains.changeemail

import com.imobly.imobly.domains.users.LandLordDomain
import java.time.LocalDateTime

class ChangeEmailLandLordDomain(
    var id: String? = null,
    var landLord: LandLordDomain,
    var token: String,
    var email: String,
    var moment: LocalDateTime = LocalDateTime.now()
)