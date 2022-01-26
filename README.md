# DraftKings DFS Multi-Lineup Optimizer (NFL)

**Background:** Every day, thousands of Daily Fantasy Sports players try their hand at building the ultimate lineup in hopes of taking home their share of a large cash prize pool - sometimes eclipsing $1M. Daily Fantasy Sports, or DFS, is a variation of traditional fantasy sports games, where players build lineups of professional athletes and earn points based on the athletes' real-life statistical performances. Unlike traditional fantasy sports games, played over the course of an entire season, DFS contests can be as short as a single professional game and rarely last over a week. Players utilizing online platforms, like DraftKings and FanDuel, pay an entry fee for each lineup constructed, with some contests allowing up to 150 lineups per player. Available DFS contests cover a range of different sporting events, and varying entry fees make the games accessible to almost anyone. 

**Problem:** Build a tool to construct 150 unique lineups for the multi-lineup (150) NFL weekly contests offered on DraftKings. Per DraftKings football contest rules, each lineup must consist of 1 quarterback (QB), 2 running backs (RB), 3 wide receivers (WR), 1 tight tnd (TE), 1 flex player (RB/WR/TE), and 1 defense/special teams (DST). DraftKings assigns each player a salary, and the combined salaries of the players in a given lineup cannot exceed $50,000. The popularity of fantasy sports has created a demand for individual score projections, and there are several online resources available to choose from - this project utilizes FantasyPros projections. Potential lineups can be compared using aggregate player projections, with higher projected scores favored over lower ones. This is a variation of the 0/1 Knapsack Problem, with an additional constraint defining the quantity of each position required in a valid lineup. The player projections are their 'value' and the salaries are their 'cost', with the maximum capacity of the 'knapsack' represented by the $50,000 lineup salary cap. The Knapsack Problem is NP-complete and has no known polynomial-time solution, and solving by brute force has a time complexity of O(2^n). Even if we only consider the top QB, top 2 RB, top 3 WR, top TE, and DST for each of the 32 NFL teams, it would take approximately 54.5 years to evaluate every possible lineup at a rate of 1,000,000 lineups per second.

- QB - C(32, 1) = 32
- RB - C(64, 2) = 2016
- WR - C(92, 3) = 142880
- TE - C(32, 1) = 32
- FLEX - C(182, 1) = 182
- DST - C(32, 1) = 32
- 32 * 2016 * 142880 * 32 * 182 * 32 = 1,717,842,298,798,080 total lineups -> @ 1,000,000 lineups/second -> 28,630,704 minutes -> 19,882 days -> 54.47 years

**My Solution:** There are several pre-packed resources available that utilize Linear Programming and/or Dynamic Programming and Memoization to solve the lineup optimization problem; however, the purpose of this exercise is to generate a custom/unique solution (and write more code) so those are not considered. Perhaps the most important distinction to make between this problem and a more classic example of the Knapsack Problem is that the 'values' are not concrete - they are projected values, and the actual statistical performance of players will rarely, if ever, exactly match their projections. This introduces an element of randomness and also the opportunity for some human discretion. If projections were consistently accurate these games would have no allure, the highest projected lineups would score the most points and there would be very little variation between the lineup pools. Instead, players are tasked with using their knowledge and resouces to identify opportunities for over performance, while trying to avoid the pitfalls of potential underperformance. 



![image](https://user-images.githubusercontent.com/61070285/151221467-be888c61-06f0-41dc-823d-845fce058bf3.png)

![image](https://user-images.githubusercontent.com/61070285/151221653-2b561240-ba3a-4231-93e7-0e3405504141.png)

![image](https://user-images.githubusercontent.com/61070285/151221852-f24eba92-8a01-4e29-ba7d-cccd275b1b74.png)

![image](https://user-images.githubusercontent.com/61070285/151221975-c02ac858-dbdf-4311-8f29-8ba91eb34f4a.png)

![image](https://user-images.githubusercontent.com/61070285/151222061-843b0a2e-8610-44b4-a4e2-50d58e5c449a.png)

![image](https://user-images.githubusercontent.com/61070285/151222183-d35a9725-1e1d-4385-94b7-ac45c96b1dac.png)

![image](https://user-images.githubusercontent.com/61070285/151222339-fe5cf9b1-6192-47c6-a28a-1880de0b0f9a.png)

![image](https://user-images.githubusercontent.com/61070285/151223064-648ba600-ada9-48ce-b3e6-d47732d032ff.png)

![image](https://user-images.githubusercontent.com/61070285/151223148-923346be-f164-4a65-8fbc-3113d53b9966.png)




















