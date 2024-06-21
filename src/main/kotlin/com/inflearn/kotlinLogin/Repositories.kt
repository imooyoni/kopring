package com.inflearn.kotlinLogin

import org.springframework.data.repository.CrudRepository

interface MemberRepository:CrudRepository<Member,Long>{
    fun findByUserId(userId:String):Member
    fun findByPassword(password:String):Member
}

interface MemberGroupRepository:CrudRepository<MemberGroup, Long>{
    fun findByGroupId(groupId:String):MemberGroup
    fun findByGroupName(groupName:String):MemberGroup
}