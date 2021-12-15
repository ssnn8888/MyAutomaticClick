package com.example.myautomaticclick.util

import android.content.Context

/**
 * Copyright (c)2019 南京欣网互联网络科技有限公司
 *
 * @类描述:
 * @创建人: zw
 * @创建时间: 2021/12/15
 */
 fun startDuokan(context: Context) {
    context.startActivity(context.packageManager.getLaunchIntentForPackage("com.duokan.reader"))
}

fun sleep(time :Long){
    Thread.sleep(time)
}