/*
package com.app.lifegames

import android.support.v7.app.AppCompatActivity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast

import com.app.lifegames.alarmServices.AlarmReceiver

import java.util.Calendar

class Alarm : AppCompatActivity() {
    private var pendingIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)

        */
/* Retrieve a PendingIntent that will perform a broadcast *//*

        val alarmIntent = Intent(this@Alarm, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this@Alarm, 0, alarmIntent, 0)

        findViewById(R.id.startAlarm).setOnClickListener(View.OnClickListener { start() })

        findViewById(R.id.stopAlarm).setOnClickListener(View.OnClickListener { cancel() })

        findViewById(R.id.stopAlarmAt10).setOnClickListener(View.OnClickListener { startAt10() })
    }

    fun start() {
        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval = 8000

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval.toLong(), pendingIntent)
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show()
    }

    fun cancel() {
        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.cancel(pendingIntent)
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show()
    }

    fun startAt10() {
        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval = 1000 * 60 * 20

        */
/* Set the alarm to start at 10:30 AM *//*

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 10)
        calendar.set(Calendar.MINUTE, 30)

        */
/* Repeating on every 20 minutes interval *//*

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                (1000 * 60 * 20).toLong(), pendingIntent)


    }

}
*/
