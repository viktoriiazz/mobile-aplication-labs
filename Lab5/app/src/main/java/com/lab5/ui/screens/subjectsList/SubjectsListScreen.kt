package com.lab5.ui.screens.subjectsList

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab5.ui.theme.Lab5Theme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SubjectsListScreen(
    viewModel: SubjectsListViewModel = koinViewModel(), // initialization of viewModel by koin function koinViewModel()
    onDetailsScreen: (Int) -> Unit,
) {
    // Converting StateFlows to Compose States (from ViewModel to Compose Screen)
    val subjectsListState = viewModel.subjectListStateFlow.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(subjectsListState.value) { subject ->
                Text(
                    text = subject.title,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable(
                            interactionSource = null,
                            indication = LocalIndication.current,
                        ) { subject.id?.let { id -> onDetailsScreen(id) } }
                )
            }
        }
    }
}

/**
 * Preview can't display data from ViewModel
 */
@Preview(showBackground = true)
@Composable
private fun SubjectsListScreenPreview() {
    Lab5Theme {
//        SubjectsListScreen({})
    }
}