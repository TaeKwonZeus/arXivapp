package com.github.taekwonzeus.arxivapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.github.taekwonzeus.arxivapp.R
import com.github.taekwonzeus.arxivapp.api.ArxivEntry

@Composable
fun ArxivCard(arxivEntry: ArxivEntry) {
    OutlinedCard(
        modifier = Modifier.padding(4.dp).fillMaxWidth(),
        onClick = { /*TODO*/ }
    ) {
        Row {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.article),
                contentDescription = "Person icon",
                modifier = Modifier.padding(8.dp)
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = arxivEntry.title!!, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = arxivEntry.authors.joinToString(
                        separator = ", ",
                    ) { it },
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}