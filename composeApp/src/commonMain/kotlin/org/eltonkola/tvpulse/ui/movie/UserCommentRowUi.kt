package org.eltonkola.tvpulse.ui.movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun UserCommentRowUi(
    userComment: String,
    updateComment: (String) -> Unit
) {

    var comment by remember { mutableStateOf(TextFieldValue(userComment)) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {

        OutlinedTextField(
            value = comment,
            onValueChange = {
                comment = it
                updateComment(comment.text)
            },
            label = { Text("Your personal note") },
            modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 100.dp),
            maxLines = 5,
            shape = RoundedCornerShape(8.dp)
        )

    }
}
