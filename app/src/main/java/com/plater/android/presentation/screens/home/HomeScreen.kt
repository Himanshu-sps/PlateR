package com.plater.android.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.data.remote.dto.response.RecipeDto
import com.plater.android.domain.models.User
import com.plater.android.presentation.common.RecipeCardItem

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val userState by viewModel.userState.collectAsState()
    val user = userState?.user

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                vertical = DimensUtils.dimenDp(R.dimen.size_10)
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        // User greeting section
        UserGreetingSection(
            modifier = Modifier.padding(horizontal = DimensUtils.dimenDp(R.dimen.size_8)),
            user = user
        )

        // featured recipe list
        Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_40)))

        Text(
            modifier = Modifier.padding(horizontal = DimensUtils.dimenDp(R.dimen.size_8)),
            text = stringResource(R.string.featured),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_8)))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = DimensUtils.dimenDp(R.dimen.size_8)
            ),
            horizontalArrangement = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_4))
        ) {
            items(
                items = listOf(
                    RecipeDto(
                        id = 1,
                        name = "Greek Moussaka",
                        image = "https://cdn.dummyjson.com/recipe-images/19.webp",
                        cookTimeMinutes = 45,
                        rating = 4.5,
                        reviewCount = 32,
                        servings = 6,
                        cuisine = "Indian"
                    ),
                    RecipeDto(
                        id = 2,
                        name = "Chicken Tikka Masala",
                        image = "https://cdn.dummyjson.com/recipe-images/1.webp",
                        cookTimeMinutes = 30,
                        rating = 4.8,
                        reviewCount = 38,
                        servings = 4,
                        cuisine = "North Indian"
                    ),
                    RecipeDto(
                        id = 3,
                        name = "Beef Stroganoff",
                        image = "https://cdn.dummyjson.com/recipe-images/2.webp",
                        cookTimeMinutes = 60,
                        rating = 4.6,
                        reviewCount = 50,
                        servings = 4,
                        cuisine = "Peshawari"
                    )
                )
            ) { recipe ->
                RecipeCardItem(recipe = recipe)
            }
        }

    }
}


@Composable
private fun UserGreetingSection(
    modifier: Modifier = Modifier,
    user: User?
) {
    if (user != null) {
        Row(
            modifier = modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_12)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(DimensUtils.dimenDp(R.dimen.size_6))
            ) {
                Text(
                    text = stringResource(R.string.hello_x, "${user?.firstName}"),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = stringResource(R.string.what_are_you_cooking_today),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (!user.image.isNullOrEmpty()) {
                AsyncImage(
                    model = user.image,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1.0f)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_account),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1.0f)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(DimensUtils.dimenDp(R.dimen.size_8)),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

