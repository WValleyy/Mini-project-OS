# Monitor

## Dining Philosophers

**Problem Description**

5 philosophers dine together at the same table. Each philosopher has his own plate at the table. There is a fork between each plate. The dish served is a kind of spaghetti which has to be eaten with 2 forks. 

Each philosopher can only alternately **think** and **eat**. Moreover, a philosopher can only eat his spaghetti **when he has both a left and right fork**. Thus 2 forks will only be available when his 2 nearest neighbors are thinking, not eating. After an individual philosopher finishes eating, he will put down both forks. 

The problem is how to design a regimen (a concurrent algorithm) such that any philosopher will not starve; i.e., each can forever continue to alternate between eating and thinking, assuming that no philosopher can know when others may want to eat or think

**Solution**: This solution imposes the restriction that a philosopher may pick up her chopsticks only if both of them are available. To code this solution, we need to distinguish among three states in which we may find a philosopher. For this purpose, we introduce the following data structure: enum **{THINKING, HUNGRY, EATING}**;
  >*( Source : Abraham-Silberschatz-Operating-System-Concepts-10th)*

**Input** : 5 threads which are 5 philosophers at the begin state are thinking

**Output** : The state of philosopher **{THINKING, HUNGRY, EATING}** after each step of the program.