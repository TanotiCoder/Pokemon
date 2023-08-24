package com.example.pokemon.ui

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pokemon.R
import com.example.pokemon.ui.theme.PokemonTypography
import com.example.pokemon.utils.GlobalConst
import com.example.pokemon.viewmodel.PokemonListViewModel
import java.util.Locale

@Composable
fun PokemonCard(
    goToDetail: (bGColor: String, name: String) -> Unit,
    context: Context,
    text: String,
    url: String,
    pokemonListViewModel: PokemonListViewModel = hiltViewModel()
) {
    var checked by remember { mutableStateOf(false) }
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    val imageUrl = GlobalConst.imgUrl + getImageUrl(url) + ".png"

    val imageLoader = ImageLoader(context)
    val request =
        ImageRequest.Builder(context)
            .data(imageUrl)
            .target { drawable ->
                pokemonListViewModel.calcDominantColor(
                    drawable,
                    onFinish = { dominantColor = it })
            }.build()
    imageLoader.enqueue(request)
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                goToDetail(
                    dominantColor
                        .toArgb()
                        .toString(),
                    text
                )
            },
        colors = CardDefaults.cardColors(containerColor = dominantColor.copy(alpha = 0.5f)),
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
            ShapeComp()
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = text.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                        style = PokemonTypography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = formatValueWithLeadingZeros(url),
                        style = PokemonTypography.titleLarge
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    IconToggleButton(
                        checked = checked,
                        onCheckedChange = { checked = !checked },
                        colors = IconButtonDefaults.iconToggleButtonColors(containerColor = Color.White),
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 8.dp)
                            .clip(shape = CircleShape)
                            .size(48.dp)
                    ) {
                        if (checked) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Localized description",
                                tint = Color.Red
                            )
                        } else {
                            Icon(
                                Icons.Outlined.Favorite,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                    ShowPokemonImage(url = imageUrl, text = text, modifier = Modifier)
                }
            }
        }
    }
}

@Composable
fun ShowPokemonImage(url: String, text: String, modifier: Modifier) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = text,
        modifier = modifier
            .fillMaxWidth()
            .size(220.dp),
        alignment = Alignment.Center

    ) {
        val state = painter.state
        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
            LottieCompositionAnimation(modifier = Modifier, raw = R.raw.animation_pokemon_img)
        } else {
            SubcomposeAsyncImageContent()
        }
    }
}

@Composable
fun ShapeComp() {
    val backgroundColorSpan = MaterialTheme.colorScheme.background
    Canvas(modifier = Modifier.size(120.dp)) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val formWidth = (canvasWidth * 2)
        val xPos = (canvasWidth / 2)

        drawArc(
            color = backgroundColorSpan,
            270f,
            360f,
            useCenter = true,
            size = Size(formWidth + 60, 320f),
            topLeft = Offset(x = -xPos, y = canvasHeight - 360)
        )
    }
}

@Composable
fun LottieCompositionAnimation(modifier: Modifier, raw: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(raw))

    LottieAnimation(
        modifier = modifier.size(120.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}

fun getImageUrl(url: String): String {
    return url.split("/".toRegex()).dropLast(1).last()
}

fun formatValueWithLeadingZeros(value: String): String {
    val a = value.split("/".toRegex()).dropLast(1).last().toInt()
    return String.format("#%04d", a)
}