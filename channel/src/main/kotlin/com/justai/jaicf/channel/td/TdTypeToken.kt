package com.justai.jaicf.channel.td

import com.justai.jaicf.generic.ChannelTypeToken

typealias TdTypeToken = ChannelTypeToken<TdRequest, TdReactions>

val td: TdTypeToken = ChannelTypeToken()