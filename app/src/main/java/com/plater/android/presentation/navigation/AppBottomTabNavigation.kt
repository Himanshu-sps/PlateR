package com.plater.android.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.navigation.NavDestination
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.core.utils.bouncingClickable

data class BottomNavItem(
    val route: ScreenRoutes.MainSubGraph,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val label: String
)

fun NavDestination?.isSameRoute(route: ScreenRoutes.MainSubGraph): Boolean {
    val targetRoute = route::class.qualifiedName ?: route::class.simpleName ?: return false
    return this?.route == targetRoute
}

@Composable
fun AppBottomTabBar(
    items: List<BottomNavItem>,
    currentDestination: NavDestination?,
    onItemSelected: (ScreenRoutes.MainSubGraph) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(
                shape = RectangleShape, shadow = Shadow(
                    radius = DimensUtils.dimenDp(R.dimen.size_4),
                    spread = DimensUtils.dimenDp(R.dimen.size_4),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                    offset = DpOffset(
                        DimensUtils.dimenDp(R.dimen.size_4),
                        DimensUtils.dimenDp(R.dimen.size_4)
                    )
                )
            )
            .background(color = MaterialTheme.colorScheme.background)
            .padding(
                all = DimensUtils.dimenDp(R.dimen.size_8)
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = currentDestination.isSameRoute(item.route)
            BottomTabItem(
                modifier = Modifier.weight(1f),
                item = item,
                selected = isSelected,
                onClick = { onItemSelected(item.route) }
            )
        }
    }
}

@Composable
private fun BottomTabItem(
    modifier: Modifier = Modifier,
    item: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val iconTint = if (selected) colorScheme.primary else colorScheme.onSurfaceVariant
    val labelColor = if (selected) colorScheme.primary else colorScheme.onSurfaceVariant

    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .bouncingClickable(
                    onClick = onClick
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = if (selected) item.selectedIcon else item.unselectedIcon),
                contentDescription = item.label,
                tint = iconTint
            )
            if (selected) {
                Spacer(modifier = Modifier.height(DimensUtils.dimenDp(R.dimen.size_2)))
                Text(
                    text = item.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = labelColor
                )
            }
        }
    }

}