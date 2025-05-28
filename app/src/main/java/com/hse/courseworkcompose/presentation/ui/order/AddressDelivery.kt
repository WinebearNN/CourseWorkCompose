package com.hse.courseworkcompose.presentation.ui.order

import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hse.courseworkcompose.R
import com.hse.courseworkcompose.presentation.viewmodel.order.OrderViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.SearchType
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.SuggestOptions
import com.yandex.mapkit.search.SuggestResponse
import com.yandex.mapkit.search.SuggestSession
import com.yandex.mapkit.search.SuggestType
import com.yandex.runtime.image.ImageProvider


private const val TAG = "SpecifyAddressScreen"
private const val RESULT_NUMBER_LIMIT = 5
private var searchSession: Session? = null
private val searchManager =
    SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)

private val suggestSession = searchManager.createSuggestSession()

private val SUGGEST_OPTIONS: SuggestOptions = SuggestOptions().setSuggestTypes(
    SuggestType.GEO.value or
            SuggestType.BIZ.value or
            SuggestType.TRANSIT.value
)


@Composable
fun SpecifyAddressScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {



    val context = LocalContext.current





    val focusManager = LocalFocusManager.current

    val imageProvider = ImageProvider.fromResource(context, R.drawable.point)

    var latitude by remember { mutableStateOf("56.327402")}
    var longitude by remember { mutableStateOf("44.007066") }

    var point by remember {
        mutableStateOf<Point>(
            Point(latitude.toDouble(), longitude.toDouble()),
        )
    }




    val mapView = MapView(context)

    val placemark = remember {
        val pm = mapView.mapWindow.map.mapObjects.addPlacemark()
        pm.setIcon(imageProvider)
        pm.setIconStyle(IconStyle().setScale(0.4f))
        pm
    }
    mapView.apply {
        layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        mapWindow.map.move(
            CameraPosition(
                point,
                /* zoom = */ 17.0f,
                /* azimuth = */ 0.0f,
                /* tilt = */ 0.0f
            )
        )
    }



    var address by remember { mutableStateOf("") }
    var suggestions = remember { mutableListOf<String>() }
    var isSuggestionListVisible by remember { mutableStateOf(true) }










    MapKitFactory.getInstance().onStart()



    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                mapView
            },
            update = { map ->
                map.mapWindow.map.move(
                    CameraPosition(
                        point,
                        /* zoom = */ 17.0f,
                        /* azimuth = */ 0.0f,
                        /* tilt = */ 0.0f
                    )
                )
                placemark.geometry=point
            }
        )

        Column {
            TextField(
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth(),
                value = address,
                onValueChange = { newQuery ->
                    address = newQuery
                    requestSuggest(newQuery, mapView, suggestions)
                    isSuggestionListVisible =
                        newQuery.isNotEmpty() && suggestions.isNotEmpty()
                    Log.d(TAG, "$isSuggestionListVisible")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences,
                    autoCorrectEnabled = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ),

//                keyboardActions = KeyboardActions(
//                    onSearch = {
//                        // Действие при нажатии на поиск (например, фильтрация списка)
////                            viewModel.searchItems(searchQuery)
//                    }
//                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Поиск",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    if (address.isNotEmpty()) {
                        IconButton(
                            onClick = { address = "" }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Очистить",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                label = { Text("Поиск") },
            )
            if (isSuggestionListVisible) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .heightIn(max = 200.dp)
                ) {
                    items(suggestions.size) { suggestion ->
                        Text(
                            text = suggestions[suggestion],
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    focusManager.clearFocus()
                                    address = suggestions[suggestion]
                                    isSuggestionListVisible = false
                                    searchByAddress(address, mapView) { newPoint ->
                                        point = newPoint
                                        latitude = newPoint.latitude.toString()
                                        longitude = newPoint.longitude.toString()
                                    }
                                }
                                .padding(8.dp)
                        )
                    }
                }
            }
        }

        Button(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 15.dp)
                .height(56.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            onClick = {
                viewModel.saveData(
                    context = context,
                    address = address,
                    longitude = longitude,
                    latitude = latitude
                )
                navController.popBackStack()
            },
            border = BorderStroke(
                width = 1.dp,
                color = Color.Gray
            ),
            enabled = address.isNotBlank(),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 2.dp,
                pressedElevation = 1.dp,
                focusedElevation = 2.dp,
                hoveredElevation = 2.dp
            ),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                disabledContentColor = Color.Black
            )
        ) {
            Text(
                text = "Подтвердить",
                color = Color.White,
                fontSize = 15.sp,
                letterSpacing = 1.sp
            )
        }


    }
}


private fun searchByAddress(
    query: String,
    mapView: MapView,
    onResult: (Point) -> Unit
) {

    val searchOptions = SearchOptions().apply {
        searchTypes = SearchType.GEO.value
    }



    searchSession = searchManager.submit(
        query,
        Geometry.fromPoint(mapView.mapWindow.map.cameraPosition.target),
        searchOptions,
        object : Session.SearchListener {
            override fun onSearchResponse(response: Response) {
                if (response.collection.children.isNotEmpty()) {
                    val resultLocation =
                        response.collection.children.first().obj!!.geometry
                    val point = resultLocation.first().point
                    if (point != null) {
                        Log.d(TAG, "Карта перемещена на ${point.latitude} ${point.longitude}")
                        onResult(point)
                    } else {
                        Log.d("Search", "Координаты для первого результата не найдены")
                    }
                } else
                    Log.d(TAG, "Результаты для запроса '$query' не найдены")
            }

            override fun onSearchError(error: com.yandex.runtime.Error) {
                Log.e(TAG, "Ошибка поиска")
            }
        }
    )
}

private fun requestSuggest(query: String, mapView: MapView, list: MutableList<String>) {
    val visibleRegion = mapView.mapWindow.map.visibleRegion // Получение текущей видимой области
    val boundingBox =
        BoundingBox(visibleRegion.bottomLeft, visibleRegion.topRight) // Создание BoundingBox


    // Выполнение запроса подсказок
    suggestSession.suggest(
        query,
        boundingBox,
        SUGGEST_OPTIONS,
        object : SuggestSession.SuggestListener {


            override fun onResponse(p0: SuggestResponse) {
                list.clear()
                println(p0.items.toString())
                for (i in 0 until RESULT_NUMBER_LIMIT.coerceAtMost(p0.items.size)) {
                    list.add(p0.items[i].displayText!!)
                }
                Log.d(TAG, "onResponse $list")

            }

            override fun onError(error: com.yandex.runtime.Error) {
                Log.e(TAG, "Ошибка поиска $error")

            }
        })
}


@Preview(showBackground = true)
@Composable
private fun SpecifyAddressPreview() {
    SpecifyAddressScreen(navController = rememberNavController())
}