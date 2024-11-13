package com.moberganalytics.tutorials

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moberganalytics.tutorials.ui.theme.TutorialTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TutorialTheme {
                TipTimeApp()
            }
        }
    }
}

@VisibleForTesting
internal fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
    var tip = tipPercent / 100 * amount
    if (roundUp) {
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipTimeApp() {

    var inputAmount: String by remember { mutableStateOf("") }
    // we import androidx.compose.runtime.[sg]etValue to allow us to get and set the
    // value of this variable without using the MutableState.value property
    val amount = inputAmount.toDoubleOrNull() ?: 0.0

    var inputTip: String by remember { mutableStateOf("") }
    val tipPercent = inputTip.toDoubleOrNull() ?: 0.0

    var roundUp: Boolean by remember { mutableStateOf(false) }

    val tip = calculateTip(amount, tipPercent, roundUp)

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Tip Time",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {
            Column (
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 40.dp)
                    .verticalScroll(rememberScrollState())
                    .safeDrawingPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text (
                    text  = stringResource(R.string.calculate_tip),
                    modifier = Modifier
                        .padding(bottom = 16.dp, top = 40.dp)
                        .align(alignment = Alignment.Start)
                )
                EditNumberField(
                    label = R.string.bill_amount,
                    leadingIcon = R.drawable.money,
                    value = inputAmount,
                    onValueChange = { inputAmount = it },
                    keyboardOpts = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                        // imeAction will configure the "submit" button on the keyboard
                    ),
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .fillMaxWidth()
                )
                EditNumberField(
                    label = R.string.how_was_the_service,
                    leadingIcon = R.drawable.percent,
                    value = inputTip,
                    onValueChange = { inputTip = it },
                    keyboardOpts = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .fillMaxWidth()
                )
                RoundTheTipRow(
                    roundUp = roundUp,
                    onRoundUpChanged = { roundUp = it },
                )
                Spacer (modifier = Modifier.height(80.dp))
                Text (
                    text = stringResource(R.string.tip_amount, tip),
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/*
    Composition -> description of the UI built by Jetpack Compose
    when state changes, Compose re-executes the affected composable functions with the
    new state -> update UI (i.e. recomposition)

    Compose runs composables during *iinitial composition*
    *Recomposition* -> Compose re-executes composables that may have changed due to data
    changes -> updates the Composition to reflect changes
    this is the only way to change the Composition after initial composition.

    "State" and "MutableState" types make the state "observable" i.e. tracked by Compose
    "State" -> immutable -> can only be read
    "MutableState" -> can change
    "mutableStateOf()" -> creates an observable "MutableState" object with some initial value
      -> the value returned holds state, is mutable & observable (i.e. causes recomposition on change)
      -> recomposition resets the state of the composable

    "remember" -> a composable that let's you store a state value across recompositions

    try to make composables stateless (or at least own as little state as possible)
    by hoisting state to parent composables
    -> this typically involves adding 2 args to a composable -> value & onValueChange
       i.e. state & onStateChange
 */
@Composable
fun EditNumberField(
    @StringRes label: Int, // StringRes -> type-safety for string resources
    @DrawableRes leadingIcon: Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOpts: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    // Compose keeps track of composable that use state `.value` props to trigger recomps
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(stringResource(label)) },
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
        keyboardOptions = keyboardOpts,
        modifier = modifier,
    )
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.round_up_tip), modifier = modifier)
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    var appState: Int by remember { mutableIntStateOf(1) }
    var totalSqueezes: Int by remember { mutableIntStateOf(2) }
    var squeezeCount: Int by remember { mutableIntStateOf(0) }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lemonade",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {
            when (appState) {
                1 -> LemonTextAndImage(
                    messageID = R.string.lemon_tree,
                    imageID = R.drawable.lemon_tree,
                    descID = R.string.lemon_tree_desc,
                    onClick = {
                        appState = 2
                        totalSqueezes = (2..4).random()
                    },
                )
                2 -> LemonTextAndImage(
                    messageID = R.string.lemon_squeeze,
                    imageID = R.drawable.lemon_squeeze,
                    descID = R.string.lemon_squeeze_desc,
                    onClick = {
                        squeezeCount += 1
                        if (squeezeCount == totalSqueezes) {
                            squeezeCount = 0
                            appState = 3
                        }
                    },
                )
                3 -> LemonTextAndImage(
                    messageID = R.string.lemon_drink,
                    imageID = R.drawable.lemon_drink,
                    descID = R.string.lemon_drink_desc,
                    onClick = { appState = 4 },
                )
                else -> LemonTextAndImage(
                    messageID = R.string.lemon_restart,
                    imageID = R.drawable.lemon_restart,
                    descID = R.string.lemon_restart_desc,
                    onClick = { appState = 1 },
                )
            }
        }
    }
}

@Composable
fun LemonTextAndImage(
    messageID: Int,
    imageID: Int,
    descID: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(16.dp),
        ) {
            Image(
                painter = painterResource(imageID),
                contentDescription = stringResource(descID)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = stringResource(messageID))
    }
}

@Composable
fun DiceRollerApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        DiceWithButtonAndImage(
            modifier = Modifier
                .fillMaxSize() // take up all the screen space
                .wrapContentSize(align = Alignment.Center) // make the space at least as large as contents. if size is larger, use align to determine how to oarrange componenets within the space
        )
    }
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    // composables are stateless by default -> can be recomposed at any time by the system
    // "remember" -> composable that allows you to store "state"
    //            -> requires a function to be passed, e.g. mutableStateOf which returns an observable
    //            -> when its value changes, a recomp is triggered
    var result: Int by remember { mutableIntStateOf(1) }
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    Column  (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = result.toString()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                result = (1..6).random()
            }
        ) {
            Text(text = stringResource(R.string.roll))
        }
    }
}
