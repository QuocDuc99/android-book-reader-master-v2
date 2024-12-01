package org.geometerplus.zlibrary.bookreader.model


import java.io.Serializable


/**
 * Created by DucNA on 03/04/2024.
 * QIG
 */

data class Attachments(
    var id: Int? = null,
    var fileName: String? = null,
    var originName: String? = null,
    var fileType: String? = null,
    var size: Int? = null,
    var url: String? = null,
    var privateUrl: String? = null,
    var categoryTypeId: Int? = null,
) : Serializable{
    // check expand/collapse view
    var isExpand = false

    // danh sách chương bài
    var listChapter: List<TableOfContents>? = null
}