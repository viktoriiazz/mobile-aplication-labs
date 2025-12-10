package com.lab4.di

import com.lab4.data.db.DatabaseStorage
import com.lab4.data.db.Lab4Database
import com.lab4.ui.screens.subjectDetails.SubjectDetailsViewModel
import com.lab4.ui.screens.subjectsList.SubjectsListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Lab4Database> { DatabaseStorage.getDatabase(androidContext()) }

    single { get<Lab4Database>().subjectsDao }
    single { get<Lab4Database>().subjectLabsDao }

    viewModel { SubjectsListViewModel(get()) }
    viewModel { SubjectDetailsViewModel(get()) }
}
