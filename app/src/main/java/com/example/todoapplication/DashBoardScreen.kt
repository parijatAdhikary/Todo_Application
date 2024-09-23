package com.example.todoapplication

import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Calendar

@Composable
fun DashBoardScreen(navController: NavController){
    DashBoardScreenLayout(navController)
}
@Composable
fun DashBoardScreenLayout(navController: NavController) {

    val activity = LocalContext.current as? Activity
    BackHandler {
        activity?.finish()
    }
    DashBoardLayout()
}

@Composable
fun DashBoardLayout() {

    val database = AppDatabase.getDatabase(LocalContext.current)
    val repository = TaskRepository(database.taskDao())
    val viewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(repository))



    var isDatePickerShowing = remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    Column(
        Modifier
            .background(colorResource(R.color.white))
            .padding(30.dp, 40.dp)
            .fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
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
                fontSize = 18.sp,
                color = colorResource(R.color.color_000b11), modifier = Modifier.padding(12.dp)
            )
            Image(
                painter = painterResource(R.drawable.round_settings_24),
                contentDescription = "Content description for visually impaired",
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        isDatePickerShowing.value = true


                        //Settings click

                    }
            )
        }
            TaskScreen(viewModel)
    }

    if (isDatePickerShowing.value) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        var context= LocalContext.current
        android.app.DatePickerDialog(
            LocalContext.current,
            { _, year, month, dayOfMonth -> selectedDate = "${dayOfMonth}_${month + 1}_$year"
                Toast.makeText(context, "Selected date : " + selectedDate, Toast.LENGTH_SHORT).show()
            },year,month,day
        ).show()
        isDatePickerShowing.value = false
    }


}



@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    var taskName by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Task Name") }
        )

        Button(onClick = {
            if (taskName.isNotBlank()) {
                viewModel.addTask(Task(name = taskName, date = "10/2/2025" ))
                taskName = ""  // Clear the input field
            }
        }) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(viewModel.tasks) { task ->
                TaskItem(task)
            }
        }

    }
}

@Composable
fun TaskItem(task: Task) {
    Text(text = task.name)
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
                                modifier = Modifier.width(36.dp).padding(0.dp,10.dp))

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
                                    modifier = Modifier.width(36.dp)
                                        .background(colorResource(R.color.google_bg_color),shape = RoundedCornerShape(16.dp))
                                        .padding(0.dp,10.dp).clickable {
                                            Toast.makeText(context,"$year - $month - $dayCount",Toast.LENGTH_SHORT).show()
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
                                    modifier = Modifier.width(36.dp).padding(0.dp,10.dp))
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
