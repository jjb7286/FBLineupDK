# DraftKings DFS Multi-Lineup Optimizer (NFL)

**Background:** Every day, thousands of Daily Fantasy Sports players try their hand at building the ultimate lineup in hopes of taking home their share of a large cash prize pools - often eclipsing $1M. Daily Fantasy Sports, or DFS, is a variation of traditional fantasy sports games, where players build lineups of professional athletes and earn points based on the athletes' real-life statistical performances. Unlike traditional fantasy sports games, played over the course of an entire season, DFS contests can be as short as a single professional game and rarely last over a week. Players utilizing online platforms, like DraftKings and FanDuel, pay an entry fee for each lineup entered, with some contests allowing up to 150 lineups per player. Available DFS contests cover a range of different sporting events, and varying entry fees make the games accessible to almost anyone. 

**Problem:** Build a tool to construct 150 unique lineups for the multi-lineup (150) NFL weekly contests offered on DraftKings. Per DraftKings football contest rules, each lineup must consist of 1 quarterback (QB), 2 running backs (RB), 3 wide receivers (WR), 1 tight tnd (TE), 1 flex player (RB/WR/TE), and 1 defense/special teams (DST). DraftKings assigns each player a salary, and the combined salaries of the players in a valid lineup cannot exceed $50,000. The popularity of fantasy sports has created a demand for individual score projections, and there are several online resources available to choose from - this project utilizes FantasyPros projections. Potential lineups can be compared using aggregate player projections, with higher projected scores favored over lower ones. This is a variation of the 0/1 Knapsack Problem, with an additional constraint defining the quantity of each position required in a valid lineup. The player projections are their 'value' and the salaries are their 'cost', with the maximum capacity of the 'knapsack' represented by the $50,000 lineup salary cap. The Knapsack Problem is NP-complete with no known polynomial-time solution, and solving by brute force has a time complexity of O(2^n). Even if we only consider the top QB, top 2 RBs, top 3 WRs, top TE, and DST for each of the 32 NFL teams, it would take approximately 54.5 years to evaluate every possible lineup at a rate of 1,000,000 lineups per second.

- QB - C(32, 1) = 32
- RB - C(64, 2) = 2016
- WR - C(92, 3) = 142880
- TE - C(32, 1) = 32
- FLEX - C(182, 1) = 182
- DST - C(32, 1) = 32
- 32 * 2016 * 142880 * 32 * 182 * 32 = 1,717,842,298,798,080 total lineups -> @ 1,000,000 lineups/second -> 28,630,704 minutes -> 19,882 days -> 54.47 years

**One Solution:** There are several pre-packaged resources available that utilize Linear Programming and/or Dynamic Programming and Memoization to solve the lineup optimization problem; however, the purpose of this exercise is to generate a custom/unique solution (and write more code) so these are not considered. An important distinction to make between this problem and a more classic example of the Knapsack Problem is that the 'values' are not concrete. They're projections, and the actual statistical performance of players will rarely, if ever, exactly match their projections. This introduces an element of randomness and also the opportunity for some human discretion. If projections were consistently accurate DFS would lose its allure, and there would be little to no variation between the lineups in the contests. Instead, players are tasked with using their knowledge and resouces to identify opportunities for over performance, while trying to avoid the pitfalls of potential underperformance. 

The goal is to 'pack' as many projected points into the lineup while remaining below the $50,000 salary cap, so ranking players on the basis of Salary/Projected Points gives us a cost-per-point metric for comparison. For example, WR Justin Jefferson has a salary of $8200 and is projected to score 19.90 points, yielding a cost of $412.06 per projected point. WR DeAndre Hopkins has a salary of $6200 and is projected to score 15.60 points, yielding a cost of $397.44 per projected point. One can argue that, by comparison, DeAndre Hopkins is the better value as each projected point costs less. Further, Hopkins requires $2000 less cap space than Jefferson, which can be allocated to other positions. Sorting the position lists on an ascending cost-per-point basis ranks the players by 'value' from greatest to least, but still doesn't reduce the time complexity.

Intuitively, it seems reasonable that the optimal lineup is comprised of a set of players somewhere in the top half of their respective value sorted position lists. This thought lends itself to the idea of shortening the positions lists to remove the 'least valuable' players; however, the proper elimination threshold is difficult to determine and still does not guarantee a reasonable run time. In this example, a heuristic approach is utilized to speed up runtime. An element of randomness and uncertainly already exists in the uncertainty of the projected scores, so some degree of randomness in the player selection process seems appropriate.     





![image](https://user-images.githubusercontent.com/61070285/151268303-647e7530-807f-4cd1-857b-94bcafcd61ab.png)


**DATA:**
After pre-registering on DraftKings for an upcoming contest, the entry IDs and player names, positions, IDs, salaries, and points per game are available to export as a single CSV file, as shown below. 
![image](https://user-images.githubusercontent.com/61070285/151221467-be888c61-06f0-41dc-823d-845fce058bf3.png)
FantasyPros.com offers a plethera of resources and research, including player rankings, projections, game odds (over/unders, point spreads), matchup ratings, etc. These are also available to export as csvs organized by position. 

**GUI:**
- QB Tab - The user can compare the Over/Unders, Salaries, and Projections of the available QBs. When a QB is selected, the QB's game Location, Date & Time, Matchup Rating, Matchup Letter Grade, and Average Performance vs Projection are provided in the Player Info section. Additionally, a table populates showing the selected QB's Offensive Teammates. Exactly 1 QB must be locked for the Lineup Generator to run. When a QB is locked, the QB Stacks table below populates with the top 2 RB, TE, and 3 WR on the same team as the QB. A maximum of 150 lineups are available for QB-Teammate stacks, and the user can decide how many stacks (if any) they would like to play for each of the 6 QB Teammates shown. The QB Stack table remains in view for quick reference as the user navigates through the other position tabs. 
![image](https://user-images.githubusercontent.com/61070285/151221653-2b561240-ba3a-4231-93e7-0e3405504141.png)

- RB Tab - The user can compare the Over/Unders, Projected Roster %s, Salaries, and Projections of the available RBs. When a RB is selected, the RB's game Location, Date & Time, and Point Spread are provided in the Player Info section. The slider allows the user to customize the projection for the selected player, and a maximum of 1 RB can be selected as a lock in a chosen percentage of the total lineups. Additionally, the RB Position Parameters for the RB Group allows the user to set a minimum projection threshold for consideration for lineups, and set an upper bound for the maximum percentage of lineups non-locked RBs can appear on. Lastly, the FLEX List checkbox allows the user to decide whether or not the RB position is included in the list of available FLEX players. 
![image](https://user-images.githubusercontent.com/61070285/151221852-f24eba92-8a01-4e29-ba7d-cccd275b1b74.png)

- WR Tab - The user can compare the Over/Unders, Projected Roster %s, Salaries, and Projections of the available WRs. When a WR is selected, the WR's game Location, Date & Time, and Point Spread are provided in the Player Info section. The slider allows the user to customize the projection for the selected player, and a maximum of 2 WRs can be selected as a lock in a chosen percentage of the total lineups. Additionally, the WR Position Parameters for the WR Group allows the user to set a minimum projection threshold for consideration for lineups, and set an upper bound for the maximum percentage of lineups non-locked WRs can appear on. Lastly, the FLEX List checkbox allows the user to decide whether or not the WR position is included in the list of available FLEX players. 
![image](https://user-images.githubusercontent.com/61070285/151221975-c02ac858-dbdf-4311-8f29-8ba91eb34f4a.png)

- TE Tab - The user can compare the Over/Unders, Projected Roster %s, Salaries, and Projections of the available TEs. When a TE is selected, the TE's game Location, Date & Time, and Point Spread are provided in the Player Info section. The slider allows the user to customize the projection for the selected player, and a maximum of 1 TE can be selected as a lock in a chosen percentage of the total lineups. Additionally, the TE Position Parameters for the TE Group allows the user to set a minimum projection threshold for consideration for lineups, and set an upper bound for the maximum percentage of lineups non-locked RBs can appear on. Lastly, the FLEX List checkbox allows the user to decide whether or not the TE position is included in the list of available FLEX players. 
![image](https://user-images.githubusercontent.com/61070285/151222061-843b0a2e-8610-44b4-a4e2-50d58e5c449a.png)

- DST Tab - The user can compare the Over/Unders, Projected Roster %s, Salaries, and Projections of the available DSTs. When a DST is selected, the DST's game Location, Date & Time, and Point Spread are provided in the Player Info section. The slider allows the user to customize the projection for the selected player, and a maximum of 1 DST can be selected as a lock in a chosen percentage of the total lineups. Additionally, the DST Position Parameters for the DST Group allows the user to set a minimum projection threshold for consideration for lineups, and set an upper bound for the maximum percentage of lineups non-locked DSTs can appear on. Lastly, the No Conflict checkbox allows the user to decide whether or not DSTs can appear in the same lineup as opposing offensive players, to eliminate potential offsetting scores. 
![image](https://user-images.githubusercontent.com/61070285/151222183-d35a9725-1e1d-4385-94b7-ac45c96b1dac.png)

- Matchups Tab - The user can compare the Over/Unders and Point Spreads of the NFL games available to pick fantasy players from. When a matchup is selected, the Matchup Controls allow the user to adjust the scores by a percentage (+/-) for all non-locked RBs, WRs, and TEs for both home and away teams separately. This allows the user to adjust projections for several players based on overall matchup rather than having to adjust scores one-by-one.   
![image](https://user-images.githubusercontent.com/61070285/151222339-fe5cf9b1-6192-47c6-a28a-1880de0b0f9a.png)

- Lineups Tab - After the user has locked a QB, input the number of stack lineups, and adjusted the settings in each tab, they can generate the lineups. When lineup generation is complete, the Lineups Tab is enabled for selection and the table displays 150 unique lineups and their projections. If the user is satisfied with the lineup list, they can save and export to csv for upload to DraftKings.  
![image](https://user-images.githubusercontent.com/61070285/151223064-648ba600-ada9-48ce-b3e6-d47732d032ff.png)

- Final CSV - Once the user clicks the save end export button, the player IDs for each lineup are added to the original csv downloaded from DraftKings and written as a new csv file named 'final.' The final sheet can be uploaded to DraftKings utilizing the Edit Lineup tool, and all 150 lineups will be updated and ready to play. 
![image](https://user-images.githubusercontent.com/61070285/151223148-923346be-f164-4a65-8fbc-3113d53b9966.png)

![image](https://user-images.githubusercontent.com/61070285/151393188-4cca559e-630b-4084-bbb9-2b9f4b818099.png)


**Future Additions:**



















