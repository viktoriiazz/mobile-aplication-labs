package com.lab5.ui.screens.subjectDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab5.ui.navigation.SubjectDetailsRoute
import com.lab5.ui.theme.Lab5Theme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SubjectDetailsScreen(
    route: SubjectDetailsRoute,
    viewModel: SubjectDetailsViewModel = koinViewModel(), // initialization of viewModel by koin function koinViewModel()
) {
    // Converting StateFlows to Compose States (from ViewModel to Compose Screen)
    val subjectState = viewModel.subjectStateFlow.collectAsState()
    val subjectLabsState = viewModel.subjectLabsListStateFlow.collectAsState()

    /**
        LaunchEffect(){...} - run code inside once at starting of screen
    */
    LaunchedEffect(Unit) {
        viewModel.initData(route.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Subject", fontSize = 28.sp)
        Text(
            text = "ID: ${subjectState.value?.id} Title: ${subjectState.value?.title}",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(text = "Labs", fontSize = 28.sp, modifier = Modifier.padding(top = 16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 16.dp)
        ) {
            items(subjectLabsState.value) { lab ->
                Surface(
                    shadowElevation = 8.dp,
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Lab: ID:${lab.id} Subject:${lab.subjectId} Title: ${lab.title}",
                            fontSize = 20.sp
                        )
                        Text(
                            text = "isCompleted:${lab.isCompleted} | isInProgress:${lab.inProgress}",
                            fontSize = 20.sp
                        )
                        Text(text = "description:${lab.description}", fontSize = 16.sp)
                        Text(text = "comment:${lab.comment}", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

/**
 * Preview can't be displayed because of ViewModel which has to be on screen
 */
@Preview(showBackground = true)
@Composable
private fun SubjectDetailsScreenPreview() {
    Lab5Theme {
//        SubjectDetailsScreen(1)
    }
}