package com.github.axet.bookreader.model

import java.io.Serializable


/**
 * Created by DucNA on 03/04/2024.
 * QIG
 */

data class MyStores(
  var id: Int? = null,
  var categoryId: Int? = null,
  var documentId: Int? = null,
  var isLike: Int? = null,
  var pageIndex: Int = 1,
  var currentTimeSecond: Float? = null,
  var tocId: Any? = null,
  var attachmentId: Int? = null,
  var totalPage: Int? = null,
  var totalDuration: Float? = null,
) : Serializable