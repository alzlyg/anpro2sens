package innobi.ru.ap2sensors;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textConsole;
    private TextView textLight;
    private TextView textTemp;
    private TextView textWet;
    TemperatureView temperatureView;
    private SensorManager sensorManager;
    private List<Sensor> sensors;
    private Sensor sensorLight;
    private Sensor sensorTemp;
    private Sensor sensorWet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperatureView = (TemperatureView) findViewById(R.id.temperatureView);
        temperatureView.setRadius(250);
        temperatureView.setTemperature(+15);

        textConsole = findViewById(R.id.textConsole);
        textLight = findViewById(R.id.textLight);
        textTemp = findViewById(R.id.textTemp);
        textWet = findViewById(R.id.textWet);

        // Менеджер датчиков
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Получить все датчики, какие есть
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        // Датчик освещенности (он есть на многих моделях)
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Датчик температуры
        sensorTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        // Датчик влажности
        sensorWet = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        // Регистрируем слушатель датчика освещенности
        sensorManager.registerListener(listenerLight, sensorLight,
                SensorManager.SENSOR_DELAY_NORMAL);

        if (sensorTemp != null) {
            // Регистрируем слушатель датчика температуры
            sensorManager.registerListener(listenerTemp, sensorTemp,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else textTemp.setText("Датчик температуры отсутствует");

        if (sensorWet != null) {
            // Регистрируем слушатель датчика влажности
            sensorManager.registerListener(listenerWet, sensorWet,
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else textWet.setText("Датчик влажности отсутствует");

        // Показать все сенсоры, какие есть
        showSensors();
    }

    // Если приложение свернуто, то не будем тратить энергию на получение информации по датчикам
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerLight, sensorLight);
    }

    // Вывод всех сенсоров
    private void showSensors() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Sensor sensor : sensors) {
            stringBuilder.append("name = ").append(sensor.getName())
                    .append(", type = ").append(sensor.getType())
                    .append("\n")
                    .append("vendor = ").append(sensor.getVendor())
                    .append(" ,version = ").append(sensor.getVersion())
                    .append("\n")
                    .append("max = ").append(sensor.getMaximumRange())
                    .append(", resolution = ").append(sensor.getResolution())
                    .append("\n").append("--------------------------------------").append("\n");
        }
        textConsole.setText(stringBuilder);
    }

    // Вывод датчика освещенности
    private void showLightSensors(SensorEvent event){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Light Sensor value = ").append(event.values[0])
                .append("\n").append("=======================================").append("\n");
        textLight.setText(stringBuilder);
    }

    // Вывод датчика температуры
    private void showTempSensors(SensorEvent event){
//        temperatureView.setTemperature((int)event.values[0]);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Temperature Sensor value = ").append(event.values[0])
                .append("\n").append("=======================================").append("\n");
        textTemp.setText(stringBuilder);
    }

    // Вывод датчика влажности
    private void showWetSensors(SensorEvent event){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Wet Sensor value = ").append(event.values[0])
                .append("\n").append("=======================================").append("\n");
        textWet.setText(stringBuilder);
    }

    // Слушатель датчика освещенности
    SensorEventListener listenerLight = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showLightSensors(event);
        }
    };

    // Слушатель датчика температуры
    SensorEventListener listenerTemp = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showTempSensors(event);
        }
    };

    // Слушатель датчика влажности
    SensorEventListener listenerWet = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showWetSensors(event);
        }
    };

}