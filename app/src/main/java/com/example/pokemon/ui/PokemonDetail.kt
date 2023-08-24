package com.example.pokemon.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokemon.R
import com.example.pokemon.model.pokemon_detail.TypeX
import com.example.pokemon.ui.theme.PokemonTypography
import com.example.pokemon.utils.GlobalConst
import com.example.pokemon.viewmodel.PokemonDetailViewModel
import java.util.Locale

@Composable
fun PokemonDetail(
    bgColor: Color,
    pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val response = pokemonDetailViewModel.pokemonDetail.value
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing)
        )
    )
    if (response != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = bgColor),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.pokemon_black),
                        contentDescription = "Pokemon Shadow",
                        modifier = Modifier.rotate(angle)
                    )
                    Column(Modifier.fillMaxWidth()) {
                        Text(
                            text = formatValueWithLeadingZeros2(response.id),
                            style = PokemonTypography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        )
                        ShowPokemonImage(
                            url = GlobalConst.imgUrl + response.id + ".png",
                            text = response.name,
                            modifier = Modifier
                        )
                        Text(
                            text = response.name.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.ROOT
                                ) else it.toString()
                            },
                            style = PokemonTypography.displaySmall,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        )

                        Row(
                            Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            PokemonCombineProperties(
                                text = response.types,
                                value = (response.height.toFloat() / 10).toString() + " M",
                                value2 = (response.weight.toFloat() / 10).toString() + " KG"
                            )
                        }
                        ChartUI(modifier = Modifier, text = "BSF ")
                        ChartUI(modifier = Modifier, text = "CRPF ")
                        ChartUI(modifier = Modifier, text = "AIF ")
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonTypeButton(text: List<TypeX>) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        for (i in 1..text.size)
            Card(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(text = text[i - 1].type.name, modifier = Modifier.padding(horizontal = 16.dp))
            }
    }
}

@Composable
fun PokemonCombineProperties(value: String, value2: String, text: List<TypeX>) {
    Column(
        Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PokemonTypeButton(text = text)
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            PokemonProperties(name = "Height", value = value)
            PokemonProperties(name = "Weight", value = value2)
        }
    }
}

@Composable
fun PokemonProperties(name: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
    }
}

fun formatValueWithLeadingZeros2(value: Int): String {
    return String.format("#%04d", value)
}


@Composable
fun ChartUI(modifier: Modifier, text: String) {

    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            drawRoundRect(
                color = Color.Black,
                size = Size(width = size.width, height = 16.dp.toPx()),
                cornerRadius = CornerRadius(16.dp.toPx())
            )
            drawRoundRect(
                color = Color.White,
                size = Size(
                    width = random(size.width),
                    height = 16.dp.toPx()
                ),
                cornerRadius = CornerRadius(16.dp.toPx())
            )
        }
        Text(
            buildAnnotatedString {
                append(text = text)
//                withStyle(style = SpanStyle(color = Color.White)) {
//                    append(random(100f).toString())
//                }
//                withStyle(style = SpanStyle(color = Color.Black)) {
//                    append("/100")
//                }
            }
        )
    }
}

fun random(maxV: Float): Float {
    val random = (1..maxV.toInt()).random()
    return random.toFloat()
}

fun percentage(maxV: Float, random: Float): Int {
    return ((random * 100) / maxV).toInt()
}