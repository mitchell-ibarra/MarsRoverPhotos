package com.miibarra.marsroverphotos.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import coil.request.ImageRequest
import com.google.accompanist.coil.CoilImage
import com.miibarra.marsroverphotos.data.models.PhotoListEntry
import com.miibarra.marsroverphotos.ui.viewmodels.PhotosListViewModel
import marsroverphotos.R


@Composable
fun RoverPhotosScreenBySol(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TopAppBar(
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))
            PhotosList(navController = navController)
        }
    }



}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "Mars Rover Photos",
                fontSize = 20.sp,
                maxLines = 1
            )},
        navigationIcon = {
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_mars_rover), contentDescription = "Logo", tint = Color.Unspecified)
            }
        },
        actions = {}
    )
}

//@Composable
//fun DropDownSelections(
//    modifier: Modifier = Modifier
//) {
//    val minWidth = 105.dp
//    val height = 30.dp
//    Row(
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        modifier = modifier
//    ){
//        DropDownButton(
//            modifier = Modifier.padding(6.dp),
//            text = "Rover"
//        ){
//            Text(
//                text = "First item",
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//        DropDownButton(
//            modifier = Modifier.padding(6.dp),
//            text = "Filter By"
//        ){
//            Text(
//                text = "First item",
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}
//
//@Composable
//fun DropDownButton(
//    modifier: Modifier = Modifier,
//    text: String,
//    initiallyOpened: Boolean = false,
//    content: @Composable () -> Unit
//){
//    var isOpen by remember {
//        mutableStateOf(initiallyOpened)
//    }
//
//    Column(
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = modifier
//                .border(
//                    width = 1.dp,
//                    color = Color.LightGray,
//                    shape = RoundedCornerShape(5.dp)
//                )
//                .padding(6.dp)
//        ) {
//            Text(
//                text = text,
//                fontSize = 14.sp,
//                fontWeight = FontWeight.SemiBold
//            )
//            Icon(
//                imageVector = Icons.Default.ArrowDropDown,
//                contentDescription = "To open or close the drop down",
//                tint = Color.Black,
//                modifier = Modifier
//                    .clickable {
//                        isOpen != isOpen
//                    }
//                    .scale(1f, if (isOpen) -1f else 1f)
//            )
//        }
//    }
//}

@Composable
fun PhotosList(
    navController: NavController,
    viewModel: PhotosListViewModel = hiltNavGraphViewModel()
) {
    val photoList by remember {
        viewModel.photoList
    }
    val lastPage by remember {
        viewModel.lastPage
    }
    val isLoading by remember {
        viewModel.isLoading
    }
    val loadError by remember {
        viewModel.loadError
    }

    LazyColumn(contentPadding = PaddingValues(12.dp)) {
        val itemCount = if (photoList.size % 2 == 0) {
            photoList.size / 2
        } else {
            photoList.size / 2 + 1
        }
        items(itemCount) {
            if (it >= itemCount - 1 && !lastPage && !isLoading) {
                viewModel.loadPhotosPaginated()
            }
            photoRow(rowIndex = it, entries = photoList, navController = navController)
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if (loadError.isNotEmpty()) {
            Retry(error = loadError) {
                viewModel.loadPhotosPaginated()
            }
        }
    }
}

@Composable
fun PhotoEntry(
    entry: PhotoListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PhotosListViewModel = hiltNavGraphViewModel()
) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(4.dp))
            .aspectRatio(1f)
            .background(
                Brush.linearGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    ),
                    Offset.Zero,
                    Offset.Infinite
                )
            )
            .clickable {
                navController.navigate(
                    "photo_detail_screen/${dominantColor.toArgb()}/${entry.id}"
                )
            }
    ) {
        Column {
            CoilImage(
                request = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .target {
                        viewModel.getDominantColor(it) { color ->
                            dominantColor = color
                        }
                    }
                    .build(),
                contentDescription = entry.roverName,
                fadeIn = true,
                modifier = Modifier
                    .size(165.dp)
                    .align(CenterHorizontally),
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.scale(0.5f)
                )
            }
        }
    }
}

@Composable
fun photoRow(
    rowIndex: Int,
    entries: List<PhotoListEntry>,
    navController: NavController
) {
    Column {
        Row {
            PhotoEntry(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            if (entries.size >= rowIndex * 2 + 2) {
                PhotoEntry(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun Retry(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}