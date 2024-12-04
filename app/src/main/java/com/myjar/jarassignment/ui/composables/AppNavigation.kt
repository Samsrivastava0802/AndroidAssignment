package com.myjar.jarassignment.ui.composables

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.ui.vm.JarUiState
import com.myjar.jarassignment.ui.vm.JarViewModel
import com.myjar.jarassignment.utils.AppString

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    viewModel: JarViewModel,
) {
    val navController = rememberNavController()
    val navigate = remember { mutableStateOf<String>("") }

    NavHost(modifier = modifier, navController = navController, startDestination = "item_list") {
        composable("item_list") {
            ItemListScreen(
                uiState = viewModel.uiState,
                onNavigateToDetail = { selectedItem -> navigate.value = selectedItem },
                navigate = navigate,
                navController = navController
            )
        }
        composable("item_detail/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            ItemDetailScreen(itemId = itemId)
        }
    }
}

@Composable
fun ItemListScreen(
    uiState: JarUiState,
    onNavigateToDetail: (String) -> Unit,
    navigate: MutableState<String>,
    navController: NavHostController
) {
//    if (navigate.value.isNotBlank()) {
//        val currRoute = navController.currentDestination?.route.orEmpty()
//        if (!currRoute.contains("item_detail")) {
//            navController.navigate("item_detail/${navigate.value}")
//        }
//    }

    LaunchedEffect(key1 = uiState.data) {
        Log.d("tesrt555", "ItemListScreen:  ${uiState.data}")
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(uiState.data.size) {
            ItemCard(
                item = uiState.data[it],
                onClick = { onNavigateToDetail(uiState.data[it].id) }
            )
            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = uiState.data[it].name)
//            Log.d("lion3322", "ItemListScreen: ${uiState.data[it]}")
        }
    }
}

@Composable
fun ItemCard(item: ComputerItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(text = item.name, fontWeight = FontWeight.Bold, color = Color.Black)
        item.data?.color?.let {
            Text(text = stringResource(id = AppString.color, it))
        }
        item.data?.capacity?.let {
            Text(text = stringResource(id = AppString.capicity, it))
        }
    }
}

@Composable
fun ItemDetailScreen(itemId: String?) {
    // Fetch the item details based on the itemId
    // Here, you can fetch it from the ViewModel or repository
    Text(
        text = "Item Details for ID: $itemId",
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}
