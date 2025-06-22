package ui.feature.profile.component.picture

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.profilePictures.model.ProfilePictures.Category
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.ic_check
import ui.theme.AppTheme
import ui.theme.LilitaOneFontFamily
import ui.theme.homeOnColor
import ui.theme.homePrimaryColor

@Composable
fun CategoriesChips(
    categories: List<Category>,
    selectedCategories: List<Category>,
    onSelectCategory: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(selectedCategories) {
            CategoryChip(
                category = it,
                isSelected = true,
                onSelectCategory = { onSelectCategory(it) }
            )
        }

        items(categories.filter { it !in selectedCategories }) {
            CategoryChip(
                category = it,
                isSelected = false,
                onSelectCategory = { onSelectCategory(it) }
            )
        }
    }
}

@Composable
private fun CategoryChip(
    category: Category,
    isSelected: Boolean,
    onSelectCategory: () -> Unit
) {
    val cardColor = if (isSelected) homePrimaryColor else Color.LightGray
    val textColor = if (isSelected) homeOnColor else Color.Black

    Card(
        colors = CardDefaults.cardColors(
            containerColor = cardColor,
            contentColor = textColor
        ),
        modifier = Modifier.clickable { onSelectCategory() },
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            if (isSelected) {
                Icon(
                    painter = painterResource(Res.drawable.ic_check),
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(12.dp)
                )
            }

            Text(
                text = category.name.uppercase(),
                fontSize = 14.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = LilitaOneFontFamily()
            )
        }
    }
}

@Preview
@Composable
private fun ChipPreview() {
    AppTheme {
        Column {
            CategoryChip(
                category = Category("CATEGORY", emptyList()),
                isSelected = true,
                onSelectCategory = { }
            )

            Spacer(Modifier.height(8.dp))

            CategoryChip(
                category = Category("CATEGORY", emptyList()),
                isSelected = false,
                onSelectCategory = { }
            )
        }
    }
}
