package org.bookreader.model


import java.io.Serializable


/**
 * Created by DucNA on 08/04/2024.
 * QIG
 */

data class TableOfContents(
  var id: Int? = null,
  var parentId: Int? = null,
  var parentName: String? = null,
  var title: String? = null,
  var content: String? = null,
  var type: Int? = null,
  var categoryId: Int? = null,
  var documentId: Int? = null,
  var contentType: Int? = null,
  var order: Int? = null,
  var pageIndex: Int? = null,
  var status: Int? = null,
  var durationStart: Int? = null,
  var attachmentId: Int? = null

) : Serializable {
  var isExpanded = false

  var listChildChapter: List<TableOfContents>? = null
}