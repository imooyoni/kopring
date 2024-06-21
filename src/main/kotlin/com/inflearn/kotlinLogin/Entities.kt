package com.inflearn.kotlinLogin

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Member(
    var userId:String,
    var password:String,
    @Id @GeneratedValue var id:Long?=null
)

@Entity
class MemberGroup(
    var groupId:String,
    var groupName:String,
    var userId: String,
    @Id @GeneratedValue var id:Long?=null
)