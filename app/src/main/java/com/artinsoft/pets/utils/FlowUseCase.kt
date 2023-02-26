package com.artinsoft.pets.utils

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<in Req, Res> where Res : Any? {
    abstract suspend fun run(request: Req): Flow<NetworkResponse<Res>>
}

abstract class RequestFlowUseCase<in Req>{
    abstract suspend fun run(item: Req)
}

abstract class ResponseFlowUseCase<Res> where Res : Any?{
    abstract fun run(): Flow<Res>
}