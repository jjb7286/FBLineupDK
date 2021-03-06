# DraftKings DFS Multi-Lineup Optimizer (NFL)

**Background:** Every day, thousands of Daily Fantasy Sports players try their hand at building the ultimate lineup in hopes of taking home their share of large cash prize pools - often eclipsing $1M. Daily Fantasy Sports (DFS) is a variation of traditional fantasy sports games, where players build lineups of professional athletes and earn points based on their real-life statistical performances. Unlike traditional fantasy sports games, played over the course of an entire season, DFS contests can be as short as a single game and rarely last over a week. Players utilizing online platforms, like DraftKings and FanDuel, pay an entry fee per lineup, with some contests allowing users in excess of 150 lineups per contest. DFS contests cover a range of different sporting events, and varying entry fees make the games accessible to almost anyone. 

**Problem:** Build a tool to construct 150 unique lineups for the multi-lineup (150) NFL weekly contests offered on DraftKings. Per DraftKings football contest rules, each lineup must consist of 1 quarterback (QB), 2 running backs (RB), 3 wide receivers (WR), 1 tight end (TE), 1 flex player (RB/WR/TE), and 1 defense/special teams (DST). DraftKings assigns each player a salary, and the combined salaries of the players in a valid lineup cannot exceed $50,000. The popularity of fantasy sports has created a demand for player projections, and there are several online resources available to choose from. This project utilizes FantasyPros.com projections, with the goal of assembling lineups with the highest aggregate player projections. This is essentially a variation of the 0/1 Knapsack Problem, where player projections are their 'value,' salaries are their 'cost', and the maximum capacity of the 'knapsack' the $50,000 lineup salary cap. The problem also requires an additional constraint requiring fixed quantities of each player position (1 WB, 2 RB, 3 WR. 1 TE, 1 FLEX, 1 DST). The Knapsack Problem is NP-complete with no known polynomial-time solution, and solving by brute force has a time complexity of O(2^n). Even if only the top QB, top 2 RBs, top 3 WRs, top TE, and DST for each of the 32 NFL teams are considered, it would take approximately 54.5 years to evaluate every possible lineup at a rate of 1,000,000 lineups per second.

- QB - C(32, 1) = 32
- RB - C(64, 2) = 2016
- WR - C(92, 3) = 142880
- TE - C(32, 1) = 32
- FLEX - C(182, 1) = 182
- DST - C(32, 1) = 32
- 32 * 2016 * 142880 * 32 * 182 * 32 = 1,717,842,298,798,080 total lineups -> @ 1,000,000 lineups/second -> 28,630,704 minutes -> 19,882 days -> 54.47 years

**One Solution:** There are several pre-packaged resources available that utilize Linear Programming and/or Dynamic Programming and Memoization to solve the lineup optimization problem; however, the purpose of this exercise is to generate a custom/unique solution (and write more code) so they are not considered. An important distinction to make between this problem and a more classic example of the Knapsack Problem is that the 'values' are not concrete. They're projections, and the actual statistical performance of players will rarely, if ever, exactly match their projections - introducing an element of randomness. If projections were consistently accurate, DFS would lose its allure and there would be little to no variation between the lineups in the contests. Instead, players are tasked with using their knowledge and resources to identify opportunities for over performance, while trying to avoid the pitfalls of potential underperformance. 

The goal is to 'pack' as many projected points into the lineup while remaining below the $50,000 salary cap. Ranking players on the basis of Salary/Projected Points gives us a cost-per-point metric for player comparison. For example, WR Justin Jefferson has a salary of $8200 and is projected to score 19.90 points, yielding a cost of $412.06 per projected point. WR DeAndre Hopkins has a salary of $6200 and is projected to score 15.60 points, yielding a cost of $397.44 per projected point. One can argue that, by comparison, DeAndre Hopkins is the better value as each projected point costs less. Further, Hopkins requires $2000 less cap space than Jefferson, which can be allocated to other positions. Sorting the position lists on an ascending cost-per-point basis ranks the players by 'value' from greatest to least, but still doesn't reduce the time complexity.

Intuitively, it seems reasonable that the optimal lineup is comprised of a set of players somewhere in the top half of their respective value sorted position lists. This idea lends itself to the notion of shortening the positions lists to remove the 'least valuable' players; however, the proper elimination threshold is difficult to determine and still does not guarantee a reasonable run time. In this example, a heuristic approach is utilized to speed up runtime, but completeness is sacrificed. An element of randomness already exists in the uncertainty of the projected scores, so some degree of randomness in the player selection process seems appropriate. We can randomly select a player from a position list while still being biased toward the top of the list, where the 'more valuable' players are. This is accomplished by utilizing the function Math.abs(Math.random() - Math.random()) * (position list size) to get the index of the player to select. An example of 5,000,000 iterations of the function for a list size of 100 is shown below. The higher value players, toward the beginning/top of the list, are selected with higher frequency. The function decreases in a linear fashion, selecting players of lesser value less frequently. Utilizing this selection method, lineups are constructed by 'randomly' picking players from their respective position lists in linear time. This process can be repeated any number of times specified by the user, returning the highest scoring team evaluated with a salary cap below the $50,000 maximum. A larger number of iterations takes more time to complete, but the larger sample size of lineups increases the likelihood of finding higher projected scores. The user is able to choose between 100,000 (faster) and 10,000,000 (slower) randomly generated lineups for comparison, returning the highest scoring valid lineup of the group. Speed will vary by system - on my computer, 100,000 lineups takes approximately .25 seconds to return the highest scoring team, and 10,000,000 lineups takes approximately 25 seconds to do the same. 
![image](https://user-images.githubusercontent.com/61070285/151268303-647e7530-807f-4cd1-857b-94bcafcd61ab.png)

**Common DFS Strategies:** The solution accommodates several common DFS strategies:
- QB-Skill Player Stacking - Lineups include at least one WR, TE, or RB (pass catching) on the same team as the QB to 'stack' any points received for caught passes, passing yards, and passing touchdowns between the QB and the stacked player - both players score simultaneously.   
- Locking Players - If the user has a strong conviction about a certain player(s), the player can be locked on a specific amount of lineups. If several players are locked on all, or the majority, of lineups, the correlation of lineup scores increases. This can result in a boom or bust outcome, with the majority of the lineups having a narrower range of scores. 
- Gambling Lines - Over/Unders and Point Spreads set by professional sports books provide insight about the expected outcome of a game. The Over/Under is the expected combined score of both teams in a sporting contest. The point spread is the expected margin of error of the team favorited to win. Games with comparatively higher Over/Unders are expected to provide more scoring opportunities for players. Similarly, the players on the team favorited in the point spread are expected to cumulatively outscore the players on the opposing team. 

**DATA:**
After pre-registering on DraftKings for an upcoming contest, the entry IDs and player names, positions, IDs, salaries, and average points per game are available to export as a single CSV file, shown below. 
![image](https://user-images.githubusercontent.com/61070285/151221467-be888c61-06f0-41dc-823d-845fce058bf3.png)
FantasyPros.com offers a plethora of resources and research, including player rankings, projections, game odds (over/unders, point spreads), matchup ratings, etc. These are also available to export as csvs organized by position. 

**GUI:**
- QB Tab - The user can compare the over/unders, salaries, and projections of the available QBs. When a QB is selected, the QB's game location, date & time, matchup rating, matchup letter grade, and average performance vs projection are populated into the Player Info section. Additionally, a table showing the selected QB's Offensive Teammates is shown. For this solution, it is a personal preference to play the same QB on all teams, so exactly 1 QB must be locked for the Lineup Generator to run. When a QB is locked, the QB Stacks table below populates with the top 2 RB, TE, and 3 WR on the same team as the QB. A maximum of 150 lineups are available for QB-Teammate stacks, and the user can decide how many stacks (if any) they would like to play for each of the 6 QB Teammates shown. The QB Stack table remains in view for quick reference as the user navigates through the other position tabs. 
![image](https://user-images.githubusercontent.com/61070285/151221653-2b561240-ba3a-4231-93e7-0e3405504141.png)

- RB Tab - The user can compare the over/unders, projected roster %s, salaries, and projections of the available RBs. When a RB is selected, the RB's game location, date & time, and point spread are provided in the Player Info section. The slider allows the user to customize the projection for the selected player, and a maximum of 1 RB can be selected as a lock on the user defined percentage of total lineups. Additionally, the RB Position Parameters for the RB Group allow the user to set a minimum projection threshold for consideration for lineups, and set an upper bound for the maximum percentage of lineups non-locked RBs can appear. Lastly, the FLEX List checkbox allows the user to decide whether or not the RB position is included in the list of available FLEX players. 
![image](https://user-images.githubusercontent.com/61070285/151221852-f24eba92-8a01-4e29-ba7d-cccd275b1b74.png)

- WR Tab - The user can compare the over/unders, projected roster %s, salaries, and projections of the available WRs. When a WR is selected, the WR's game location, date & time, and point spread are provided in the Player Info section. The slider allows the user to customize the projection for the selected player, and a maximum of 2 WRs can be selected as a lock on the user defined percentage of total lineups. Additionally, the WR Position Parameters for the WR Group allow the user to set a minimum projection threshold for consideration for lineups, and set an upper bound for the maximum percentage of lineups non-locked WRs can appear. Lastly, the FLEX List checkbox allows the user to decide whether or not the WR position is included in the list of available FLEX players. 
![image](https://user-images.githubusercontent.com/61070285/151221975-c02ac858-dbdf-4311-8f29-8ba91eb34f4a.png)

- TE Tab - The user can compare the over/unders, projected roster %s, salaries, and projections of the available TEs. When a TE is selected, the TE's game location, date & time, and point spread are provided in the Player Info section. The slider allows the user to customize the projection for the selected player, and a maximum of 1 TE can be selected as a lock on the user defined percentage of total lineups. Additionally, the TE Position Parameters for the TE Group allow the user to set a minimum projection threshold for consideration for lineups, and set an upper bound for the maximum percentage of lineups non-locked RBs can appear. Lastly, the FLEX List checkbox allows the user to decide whether or not the TE position is included in the list of available FLEX players. 
![image](https://user-images.githubusercontent.com/61070285/151222061-843b0a2e-8610-44b4-a4e2-50d58e5c449a.png)

- DST Tab - The user can compare the over/unders, projected roster %s, salaries, and projections of the available DSTs. When a DST is selected, the DST's game location, date & time, and point spread are provided in the Player Info section. The slider allows the user to customize the projection for the selected player, and a maximum of 1 DST can be selected as a lock on the user defined percentage of total lineups. Additionally, the DST Position Parameters for the DST Group allow the user to set a minimum projection threshold for consideration for lineups, and set an upper bound for the maximum percentage of lineups non-locked DSTs can appear. Lastly, the No Conflict checkbox allows the user to decide whether or not DSTs can appear in the same lineup as opposing offensive players - potentially eliminating offsetting scores. 
![image](https://user-images.githubusercontent.com/61070285/151222183-d35a9725-1e1d-4385-94b7-ac45c96b1dac.png)

- Matchups Tab - The user can compare the over/unders and point spreads of the NFL games available to pick fantasy players from. When a matchup is selected, the Matchup Controls allow the user to adjust the scores by a percentage (+/-) for all non-locked RBs, WRs, and TEs for both home and away teams. This allows the user to adjust projections for all non QB/DST players on the same team based on overall matchup, rather than adjust their scores one-by-one.   
![image](https://user-images.githubusercontent.com/61070285/151222339-fe5cf9b1-6192-47c6-a28a-1880de0b0f9a.png)

- Lineups Tab - The Generate Lineups button can be pressed after the user has locked a QB, input the number of stack lineups, and adjusted the settings in each tab. When lineup generation is complete, the Lineups Tab is enabled for selection. The table displays 150 unique lineups and their projections. Each lineup is valid per DraftKings requirements and adheres to the user specified criteria. If the user is satisfied with the lineup list, they can save and export to csv for upload to DraftKings. If not, the user can continue to make changes in the position and matchup tabs and generate a new list of lineups until the results are satisfactory. 
![image](https://user-images.githubusercontent.com/61070285/151223064-648ba600-ada9-48ce-b3e6-d47732d032ff.png)

- Final CSV - Once the user clicks the Save & Export to CSV button, the player IDs for each lineup are added to the original csv downloaded from DraftKings and written as a new csv file named 'final.' The final csv can be uploaded to DraftKings utilizing the Edit Lineup tool, and all 150 lineups will be updated and ready for the contest. 
![image](https://user-images.githubusercontent.com/61070285/151223148-923346be-f164-4a65-8fbc-3113d53b9966.png)

![image](https://user-images.githubusercontent.com/61070285/151393188-4cca559e-630b-4084-bbb9-2b9f4b818099.png)


**Future Additions and Room for Improvement:**
- Serialization to save session data
- Increase number of allowed locked players
- APIs for data and less csvs
- Additional metrics such as weather, turf type, indoor/outdoor, division rivalries, win/loss streaks, injuries, etc
- Database for storing historical data & statistics
- AI and other analytical tools for improved projection accuracy



















