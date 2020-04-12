# Neural-Net
A simple Neural Network made in Java.

## How the Perceptron works
A **Perceptron** is an "artificial neuron" which simply receives some inputs, and returns some outputs.
Normally those outputs are wrong, but through a long **learning process** the Perceptron starts correcting those outputs.  
Then, once the Perceptron starts getting better results, we can give him a new totally different **new situation** and it should  get it right. 

![](README/table.png)

#### But how does the Perceptron gives us an output?
To understand how the Perceptron "thinks" we need to know how is a Perceptron.  
A Perceptron has:
- **Inputs**: They are normally *n* numbers in an **array** or a **matrix** which represent different things, for example the data collected from the weather.
- **Output**: This is one number that should correspond to the **Inputs**.
- **Synapses (or weights)**: These are *n* numbers that **"add a weight"** to the Inputs to generate an output.

The **Weights** will determine how the Perceptron is calculating the **Output**. But how, *"our Perceptron needs to learn to do this by itself"* we only will tell him how to correct these **Weights**.  
At the beginning it doesn't matter how these **Weights** are initialized because, if we compare this perceptron to a new born baby, we don't know how the baby will react to new information. So our Perceptron will work the same way, at the start we can **initialize this values at random**.  
 
To generate an **Output** the Perceptron does *the sum of the multiplications between the inputs and the weights.*  
`X1*W1 + X2*W2 + ... + Xn*Wn`  

![](README/perceptron.png)

But if you do this math, the output probably won't be in the range of numbers we need, so we will need something called an **Normalizing Function**.

#### Normalizing Function
An **Normalizing Function** (*also called Activation Function*) is a function that normalizes the value given to a different range.
We will use a Normalizing Function called the **Sigmoid Function**, this function is used for binary numbers because it receives a value, and transform it to another value in the range of 0 and 1. The **Sigmoid function** is as follows:  
`sigmoid(x) = 1 / [1 + exp(-x)]`

![](README/sigmoid.png)

## Training
Now that we know how the Perceptron works we could try it and it should give us an Output. But this Output, probably won't be right, because our Perceptron is calculating it through random weights. So we will tell the Perceptron how to **adjust these weights** through a process called **Training**, so every time we ask  for a new situation the Perceptron will get better and better.  

The first thing we need to do to **Train** the Perceptron is calculate the **error**. The error is *the difference between the output given by the Perceptron, and the actual output*. We need to calculate this for every output.  
`error = output - actualOutput`  

Once we have the error, we can adjust the weights with a formula called the **Error Weighted Derivative**. That looks like this:  
`adjustments = error * inputs`

#### Learning Rate
But we can also add another value to control the slope of the adjustments. We will call this value **Learning Rate**.  
The **Learning rate** usually is a value between 0 and 1. For example if our value is 0.5, the correction of the weight will be the half of the error. This helps to avoid big jumps in the weights.  
We will the **Sigmoid Derivative** for this. which is as follows:  
`sigmoidDerivative(x) = x * (1 - x)`  
This function receives the outputs given by the Perceptron and helps the weights to be less adjusted the closer they are to the correct value.  

![](README/sigmoid_derivative.png)

So our correction or adjustments should be calculated this way:  
`adjustments = error * inputs * sigmoidDerivative(output)`  
We add these adjustments to the weights. And now our weights will get a slightly better output.

#### Repeat
This process won't work the first time, nor the second or the third. Like us, machines need time and practice to learn new things. This whole process will need to be repeated thousands of times to work, for example 20000 times.  
This is what we call **Machine learning**. The machine:
- Receives inputs.
- Processes them.
- Generates an output.
- Calculates the error.
- Adjusts itself.
- And repeat...

## References
- PolyCode - Create a simple NN: https://youtu.be/kft1AJ9WVDk && https://youtu.be/Py4xvZx-A1E
- The Coding Train - Neural Network Playlist: https://www.youtube.com/playlist?list=PLRqwX-V7Uu6Y7MdSCaIfsxc561QI0U0Tb
- The Coding Train - Intelligence Learning: https://github.com/nature-of-code/NOC-S17-2-Intelligence-Learning/tree/master/week4-neural-networks
- *Make your own Neural Network* - book by *Tariq Rashid*