package com.example.todoapplication

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.YearMonth
import java.util.Calendar
import javax.inject.Inject

var taskDate=1
var taskMonth=1
var taskYear=1

@Composable
fun DashBoardScreenLayout() {
    val myViewModel: WirelessViewModel = viewModel()
    val activity = LocalContext.current as? Activity
    BackHandler {
        activity?.finish()
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DashBoardLayout(myViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashBoardLayout(myViewModel: WirelessViewModel) {
    val currentDate = LocalDate.now()
    var month by remember { mutableStateOf(currentDate.month) }
    val year = currentDate.year
    var date by remember { mutableStateOf(currentDate.dayOfMonth) }

    taskMonth = month.value
    taskDate = date
    taskYear = year

    val database = AppDatabase.getDatabase(LocalContext.current)
    val repository = TaskRepository(database.taskDao())
    val viewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(repository))
    var isDatePickerShowing = remember { mutableStateOf(false) }
    var taskName by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("Select your task date") }
    var isPopUpShowing = remember { mutableStateOf(false) }
    var context=LocalContext.current



    Column(
        Modifier
            .background(colorResource(R.color.white))
            .padding(30.dp, 40.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "My Todo List",
                fontWeight = FontWeight.W800,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Right,
                fontSize = 22.sp,
                color = colorResource(R.color.color_000b11), modifier = Modifier.padding(12.dp)
            )
            Image(
                painter = painterResource(R.drawable.round_settings_24),
                contentDescription = "Content description for visually impaired",
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        myViewModel.navigation {
                            navigate(Routes.SettingsScreen.full)
                        }
                    }
            )
        }
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(R.drawable.round_arrow_back_24),
                contentDescription = "Content description for visually impaired",
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        if (date == 1) {
                            date = YearMonth
                                .of(year, month.value - 1)
                                .lengthOfMonth()
                            month = month.minus(1)
                        } else
                            date--
                    }
            )

            Text(
                "$date $month $year",
                fontWeight = FontWeight.W800,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Right,
                fontSize = 18.sp,
                color = colorResource(R.color.color_000b11), modifier = Modifier.padding(12.dp)
            )
            Image(
                painter = painterResource(R.drawable.round_arrow_forward_24),
                contentDescription = "Content description for visually impaired",
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        if (date == YearMonth
                                .of(year, month.value)
                                .lengthOfMonth()
                        ) {
                            month = month.plus(1)
                            date = 1
                        } else
                            date++
                    }
            )
        }




        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {

            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp)) {
                Log.d("SIZETAG", "DashBoardLayout:${viewModel.tasks.size}")
                items(viewModel.tasks) { task ->
                    TaskItem(viewModel,task)
                }
            }
            Button(onClick = {
                isPopUpShowing.value=true
            },
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)) {
                Text("Add your task")
            }




                if (isDatePickerShowing.value) {
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    android.app.DatePickerDialog(
                        LocalContext.current,
                        { _, year, month, dayOfMonth ->
                            selectedDate = "${dayOfMonth}_${month + 1}_$year"
                        }, year, month, day
                    ).show()
                    isDatePickerShowing.value = false
                }

                val keyboardController = LocalSoftwareKeyboardController.current

                if(isPopUpShowing.value){
                    Dialog(onDismissRequest = { isPopUpShowing.value = false }) {
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            tonalElevation = 8.dp,
                            modifier = Modifier
                                .padding(16.dp)
                                .background(Color.Transparent)
                        ) {

                            Box(
                                modifier = Modifier
                                    .width(350.dp)
                                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                                    .padding(16.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            colorResource(R.color.white)
                                        )
                                ) {

                                    Text("Add New Task",
                                        fontWeight = FontWeight.W700,
                                        fontFamily = FontFamily.SansSerif,
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp,
                                        color = colorResource(R.color.black),
                                        modifier = Modifier.fillMaxWidth())
                                    Spacer(Modifier.height(20.dp))

                                    TextField(
                                        value = taskName,
                                        onValueChange = { taskName = it },
                                        label = { Text("Task Name") }, modifier = Modifier
                                            .fillMaxWidth()
                                            .onFocusChanged {
                                                keyboardController?.show()
                                            }
                                    )

                                    Text(selectedDate,
                                        fontWeight = FontWeight.W800,
                                        fontFamily = FontFamily.SansSerif,
                                        textAlign = TextAlign.Center,
                                        fontSize = 16.sp,
                                        color = colorResource(R.color.color_1d1e20),
                                        modifier = Modifier
                                            .padding(0.dp, 16.dp)
                                            .fillMaxWidth()
                                            .clickable {
                                                isDatePickerShowing.value = true
                                            }
                                    )

                                    Button(onClick = {
                                        if (taskName.isNotBlank() && selectedDate.length <= 10) {
                                            viewModel.addTask(
                                                Task(
                                                    todolist = taskName,
                                                    date = selectedDate
                                                )
                                            )
                                            taskName = ""
                                            selectedDate = "Select your task date"
                                            isPopUpShowing.value = false
                                        } else
                                            Toast.makeText(
                                                context,
                                                "Please select a date and write your task",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                    }, modifier = Modifier.fillMaxWidth()) {
                                        Text("Add")
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            }
                        }
                    }
                }




        }
    }
}


@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    dialogPositive: String,
    dialogNegative: String,
) {
    AlertDialog(

        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(text = dialogPositive)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = dialogNegative)
            }
        }
    )
}

@Composable
fun TaskItem(viewModel: TaskViewModel, task: Task) {

    var context = LocalContext.current
    val filterDate="${taskDate}_${taskMonth}_$taskYear"


    val openDeleteDialog = remember { mutableStateOf(false) }
    val openEditDialog = remember { mutableStateOf(false) }

    var isDatePickerShowing = remember { mutableStateOf(false) }
    var updatedTaskName by remember { mutableStateOf("") }
    var updatedDate by remember { mutableStateOf("") }
    var isPopUpShowing = remember { mutableStateOf(false) }


    if(task.date.contentEquals(filterDate)) {
        Row(Modifier.fillMaxWidth().padding(0.dp,8.dp)
            .pointerInput(Unit) {
            detectTapGestures(
                onLongPress = {
                    openEditDialog.value=true
                }
            )
        },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(task.todolist,
                fontWeight = FontWeight.W700,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Left,
                fontSize = 16.sp,
                color = colorResource(R.color.black))

            Image(
                painter = painterResource(R.drawable.outline_delete_24),
                contentDescription = "Delete",
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        openDeleteDialog.value=true
                    }
            )
        }
        Divider(modifier = Modifier.padding(vertical = 2.dp))
    }


    if (isDatePickerShowing.value) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        android.app.DatePickerDialog(
            LocalContext.current,
            { _, year, month, dayOfMonth ->
                updatedDate = "${dayOfMonth}_${month + 1}_$year"
            }, year, month, day
        ).show()
        isDatePickerShowing.value = false
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    if(isPopUpShowing.value){
        Dialog(onDismissRequest = { isPopUpShowing.value = false }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Transparent)
            ) {

                Box(
                    modifier = Modifier
                        .width(350.dp)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                colorResource(R.color.white)
                            )
                    ) {
                        Text("Edit Existing Task",
                            fontWeight = FontWeight.W700,
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = colorResource(R.color.black),
                            modifier = Modifier.fillMaxWidth())
                        Spacer(Modifier.height(20.dp))

                        TextField(
                            value = updatedTaskName,
                            onValueChange = { updatedTaskName = it },
                            label = { Text("Task Name") }, modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged {
                                    keyboardController?.show()
                                }
                        )

                        Text(updatedDate,
                            fontWeight = FontWeight.W800,
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = colorResource(R.color.color_1d1e20),
                            modifier = Modifier
                                .padding(0.dp, 16.dp)
                                .fillMaxWidth()
                                .clickable {
                                    isDatePickerShowing.value = true
                                }
                        )

                        Button(onClick = {
                            if (updatedTaskName.isNotBlank() && updatedDate.length <= 10) {
                                viewModel.editTask(taskId = task.id,updatedTaskName,updatedDate)
                                updatedTaskName = ""
                                updatedDate = "Select your task date"
                                isPopUpShowing.value = false
                            } else
                                Toast.makeText(
                                    context,
                                    "Please select a date and write your task",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text("Add")
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }

    if(openDeleteDialog.value){
        AlertDialogExample(
            onDismissRequest = {
                openDeleteDialog.value = false
            },
            onConfirmation = {
                viewModel.deleteTask(taskId = task.id)
                openDeleteDialog.value = false
            },
            dialogTitle = "Delete Confirmation",
            dialogText = "Do you want to Delete this task ? ",
            dialogPositive = "Delete",
            dialogNegative = "Cancel"
        )
    }
    if(openEditDialog.value){
        AlertDialogExample(
            onDismissRequest = {
                openEditDialog.value = false
            },
            onConfirmation = {
                isPopUpShowing.value=true
                updatedTaskName=task.todolist
                updatedDate=task.date
                openEditDialog.value = false
            },
            dialogTitle = "Edit Confirmation",
            dialogText = "Do you want to Edit this task ? ",
            dialogPositive = "Edit",
            dialogNegative = "Cancel"
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun monthView() {
    val context=LocalContext.current
    Column(
        Modifier
            .background(colorResource(R.color.white))
            .padding(30.dp, 40.dp)
            .fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Text("Month View",
                fontWeight = FontWeight.W800,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Right,
                fontSize = 18.sp,
                color = colorResource(R.color.color_000b11), modifier = Modifier.padding(12.dp))
            Image(
                painter = painterResource(R.drawable.round_settings_24),
                contentDescription = "Content description for visually impaired",
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        //Settings click
                    }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("September - 2024",
            fontWeight = FontWeight.W800,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Right,
            fontSize = 22.sp,
            color = colorResource(R.color.color_000b11))

        Spacer(modifier = Modifier.height(20.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            val days = arrayOf( "Mo", "Tu", "We", "Th", "Fr", "Sa","Su")
            for(day in days){
                Text(day,
                    fontWeight = FontWeight.W800,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = colorResource(R.color.color_000b11),
                    modifier = Modifier.width(36.dp))

            }
        }
        var totalDaysInThisMonth=daysInMonth()
        var firstDayNo=firstDayOfMonth()
        val currentDate = LocalDate.now()
        val todayDate =currentDate.dayOfMonth
        Log.d("DayTAG", "totalDaysInThisMonth: $totalDaysInThisMonth")
        var dayCount=1

        val month =currentDate.monthValue
        val year = currentDate.year


        for(weekCount in 1..6){
            if(dayCount<=totalDaysInThisMonth){
                Spacer(modifier = Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){

                    for(day in 1..7){
                        if((weekCount==1&&firstDayNo>day)||(dayCount>totalDaysInThisMonth)){
                            Text("",
                                fontWeight = FontWeight.W500,
                                fontFamily = FontFamily.SansSerif,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                color = colorResource(R.color.color_000b11),
                                modifier = Modifier
                                    .width(36.dp)
                                    .padding(0.dp, 10.dp))

                        }
                        else {
                            if(todayDate==dayCount){
                                Text(
                                    "$dayCount",
                                    fontWeight = FontWeight.W500,
                                    fontFamily = FontFamily.SansSerif,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    color = colorResource(R.color.white),
                                    modifier = Modifier
                                        .width(36.dp)
                                        .background(
                                            colorResource(R.color.google_bg_color),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .padding(0.dp, 10.dp)
                                        .clickable {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "$year - $month - $dayCount",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                )
                            }
                            else{
                                Text(
                                    "$dayCount",
                                    fontWeight = FontWeight.W500,
                                    fontFamily = FontFamily.SansSerif,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                    color = colorResource(R.color.color_000b11),
                                    modifier = Modifier
                                        .width(36.dp)
                                        .padding(0.dp, 10.dp))
                            }

                            dayCount++
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun firstDayOfMonth(): Int{
    val currentDate = LocalDate.now()
    val month =currentDate.monthValue
    var monthString=""
    if(month<10)
        monthString="0$month"
    val year = currentDate.year
    val dateString = "$year-$monthString-01"
    val date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
    val dayOfWeek: DayOfWeek = date.dayOfWeek
    return dayOfWeek.value
}

@RequiresApi(Build.VERSION_CODES.O)
fun daysInMonth(): Int {
    val currentDate = LocalDate.now()
    val month =currentDate.month
    val year = currentDate.year
    return LocalDate.of(year, month, 1).lengthOfMonth()
}



@HiltViewModel
class DashBoardViewModel @Inject constructor(): WirelessViewModel(){
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
        finish()
    }

    override fun onNotification(id: Any?, arg: Any?) {
    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
    }
}
