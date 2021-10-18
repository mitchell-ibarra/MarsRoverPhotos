package com.miibarra.marsroverphotos.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.google.accompanist.coil.CoilImage
import com.miibarra.marsroverphotos.data.remote.responses.Photo
import com.miibarra.marsroverphotos.ui.viewmodels.PhotoDetailViewModel

@Composable
fun PhotoDetailScreen(
    dominantColor: Color,
    photoId: Int,
    navController: NavController,
    topPadding: Dp = 20.dp,
    photoSize: Dp = 165.dp,
    viewModel: PhotoDetailViewModel = hiltNavGraphViewModel()
) {
    viewModel.getPhotoDetail(photoId)
    val photoEntry by remember {
        viewModel.photoEntry
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(dominantColor)
        .padding(16.dp)
    ) {
        TopPhotoDetailSection(
            dominantColor = dominantColor,
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )

        PhotoDescription(
            photoEntry = photoEntry,
            modifier = Modifier
                .padding(
                    top = topPadding + photoSize,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(12.dp)
                .align(Alignment.BottomCenter)
        )
        Box(contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.75f)
        ) {
            CoilImage(
                request = ImageRequest.Builder(LocalContext.current)
                    .data(photoEntry?.img_src)
                    .build(),
                contentDescription = photoEntry?.id.toString(),
                fadeIn = true,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = topPadding)
            )
        }
    }
}

@Composable
fun TopPhotoDetailSection(
    dominantColor: Color,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        Color.Transparent
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}


@Composable
fun PhotoDescription(
    photoEntry: Photo?,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Text(
            text = "Rover: ${photoEntry?.rover?.name}\n" +
                    "Earth Date when Taken: ${photoEntry?.earth_date}\n" +
                    "Sol: ${photoEntry?.sol}\n" +
                    "Camera: ${photoEntry?.camera?.name}",
            textAlign = TextAlign.Left
        )
    }
}
