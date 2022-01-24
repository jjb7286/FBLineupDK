package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import application.DST;
import application.FileHelper;
import application.Lineup;
import application.LineupHelper;
import application.ListHelper;
import application.Matchup;
import application.Offense;
import application.Player;
import application.QB;
import application.RB;
import application.Stack;
import application.TE;
import application.Team;
import application.WR;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.text.DecimalFormat;

// FXML Controller Class


public class GuiController implements Initializable {
	
	// QB Tab
	@FXML private TableView<QB> qbTable;
	@FXML private TableColumn<QB, String> qbPlayerName;
	@FXML private TableColumn<QB, String> qbPlayerTeam;
	@FXML private TableColumn<QB, String> qbPlayerOpp;
	@FXML private TableColumn<QB, String> qbOverUnder;
	@FXML private TableColumn<QB, String> qbProjRost;
	@FXML private TableColumn<QB, Integer> qbSalary;
	@FXML private TableColumn<QB, Double> qbProjPoints;
	@FXML private TableColumn<QB, Double> qbCustProj;
	@FXML private TableColumn<QB, Boolean> qbExclude;
	
	
	@FXML private TableView<Offense> stackTable;
	@FXML private TableColumn<Offense, String> stackName;
	@FXML private TableColumn<Offense, String> stackPos;
	@FXML private TableColumn<Offense, Double> stackProj;
	
	// stacks area
	Stack stack;
	int maxStacks = 150;
	@FXML TextField rb1StackName;
	@FXML TextField rb2StackName;
	@FXML TextField teStackName;
	@FXML TextField wr1StackName;
	@FXML TextField wr2StackName;
	@FXML TextField wr3StackName;
	@FXML TextField rb1StackNum;
	@FXML TextField rb2StackNum;
	@FXML TextField teStackNum;
	@FXML TextField wr1StackNum;
	@FXML TextField wr2StackNum;
	@FXML TextField wr3StackNum;
	@FXML TextField totNumStacks;
	@FXML TextField numStacksLeft;
	@FXML Label qbStacks;
	
	@FXML Button qbLockButton;
	@FXML TextField qbLocation;
	@FXML TextField qbDate;
	@FXML TextField qbMatchRat;
	@FXML TextField qbMatchLet;
	@FXML TextField qbPerform;
	
	@FXML TextField numIterations;
	@FXML TextField generatorProgress;
	@FXML Button lineupGenerator;
	@FXML Button saveExport;
	
	public static QB lockedQB;
	public static DST lockedQBOpponent;
	

		
	//@FXML TextArea customProj;
	//@FXML TextArea qBMatchup;
	//@FXML Slider slider;
	
	@FXML
	public void showQbDetails() {
		
		int index = qbTable.getSelectionModel().getSelectedIndex();
		QB qb = ListHelper.qbs.get(index);
		qbLocation.setText(qb.getMatchup().getHomeTeam().getMarket());
		qbDate.setText(qb.getMatchup().getDate());
		qbMatchRat.setText(qb.getMatchupRating());
		qbMatchLet.setText(qb.getMatchupLetter());
		qbPerform.setText(qb.getAvgPerformance());
				
				
		ListHelper.createStacks(ListHelper.qbs.get(index));
		stackTable.setItems(getStacks(ListHelper.stacks));
								
	}
	
	
	// RB TAB
	@FXML private TableView<RB> rbTable;
	@FXML private TableColumn<RB, String> rbPlayerName;
	@FXML private TableColumn<RB, String> rbPlayerTeam;
	@FXML private TableColumn<RB, String> rbPlayerOpp;
	@FXML private TableColumn<RB, String> rbOverUnder;
	@FXML private TableColumn<RB, String> rbProjRost;
	@FXML private TableColumn<RB, Integer> rbSalary;
	@FXML private TableColumn<RB, Double> rbProjPoints;
	@FXML private TableColumn<RB, Double> rbCustProj;
	@FXML private TableColumn<RB, Boolean> rbExclude;
	@FXML private TableColumn<RB, String> rbLockedPercCol;
	
	@FXML TextField rbMinProj;
	@FXML TextField rbMaxTeams;
		
	@FXML TextField rbLocation;
	@FXML TextField rbDate;
	@FXML TextField rbPtSpread;
	@FXML TextField rbLockPerc;
	@FXML Label	rbSliderPerc;
	@FXML TextField rbCustomScore;
	@FXML Slider rbSlider;
	@FXML Button rbReset;
	@FXML Button rbIndReset;
	@FXML Button rbLock;
	@FXML Button rbLockReset;
	
	@FXML CheckBox rbFlex;
	
	@FXML
	public void showRbDetails() {
		int index = rbTable.getSelectionModel().getSelectedIndex();
		RB rb = ListHelper.rbs.get(index);
		rbLocation.setText(rb.getMatchup().getHomeTeam().getMarket());
		rbDate.setText(rb.getMatchup().getDate());
		rbPtSpread.setText(rb.getMatchup().getPtSpread());
		rbSlider.setValue(0);
		rbCustomScore.setText("");
		rbSliderPerc.setText("");
		rbLockPerc.setDisable(false);
		rbLockPerc.setText(rb.getLockedPerc());
		if (RB.rbLockNum > 0) {
			if (rb.getLockedPerc().equals("")) {
				rbLockPerc.setText("MAX");
				rbLockPerc.setDisable(true);
			} else {
				rbLockPerc.setText(rb.getLockedPerc());
				rbLockPerc.setDisable(true);
			}
		}
		
		
	}
	
	@FXML
	public void rbLockPress() {
		int index = rbTable.getSelectionModel().getSelectedIndex();
		RB rb = ListHelper.rbs.get(index);
		rb.setLockedPerc(rbLockPerc.getText());
		RB.rbLockNum++;
		ListHelper.lockedPlayers.add(rb);
		rbTable.refresh();
	}
	
	@FXML
	public void rbLockResetPress() {
		int index = rbTable.getSelectionModel().getSelectedIndex();
		RB rb = ListHelper.rbs.get(index);
		if (!rb.getLockedPerc().equals("")) {
			rb.setLockedPerc("");
			rbLockPerc.setText("");
			if (RB.rbLockNum > 0) {
				RB.rbLockNum--;
				ListHelper.lockedPlayers.remove(rb);
			}
			if (rbLockPerc.isDisabled()) {
				rbLockPerc.setDisable(false);
			}
		}
		rbTable.refresh();
	}
	
	@FXML
	public void rbSlider() {
		DecimalFormat df = new DecimalFormat("#.0");
		rbSliderPerc.setText(df.format(rbSlider.getValue()) + "%");
		int index = rbTable.getSelectionModel().getSelectedIndex();
		if (index > -1) {
			RB rb = ListHelper.rbs.get(index);
			double newNum = rb.getProjection() * (1 + (rbSlider.getValue()/100));
			rb.setCustom(newNum);
			rb.setCustomized(true);
			rbCustomScore.setText(df.format(newNum));
			rbTable.refresh();
		}
	}
	
	@FXML
	public void rbIndResetButton() {
		int index = rbTable.getSelectionModel().getSelectedIndex();
		if (index > -1) {
			RB rb = ListHelper.rbs.get(index);
			rb.setCustom(rb.getProjection());
			rb.setCustomized(false);
			rbCustomScore.setText("");
			rbSlider.setValue(0);
			rbSliderPerc.setText("0%");
			rbTable.refresh();
		} 
	}
	
	@FXML
	public void rbDefaultButton() {
		rbMinProj.setText("5.5");
		rbMaxTeams.setText("25");
	}
	
	// WR TAB
	@FXML private TableView<WR> wrTable;
	@FXML private TableColumn<WR, String> wrPlayerName;
	@FXML private TableColumn<WR, String> wrPlayerTeam;
	@FXML private TableColumn<WR, String> wrPlayerOpp;
	@FXML private TableColumn<WR, String> wrOverUnder;
	@FXML private TableColumn<WR, String> wrProjRost;
	@FXML private TableColumn<WR, Integer> wrSalary;
	@FXML private TableColumn<WR, Double> wrProjPoints;
	@FXML private TableColumn<WR, Double> wrCustProj;
	@FXML private TableColumn<WR, Boolean> wrExclude;
	@FXML private TableColumn<WR, String> wrLockedPercCol;
	
	@FXML TextField wrMinProj;
	@FXML TextField wrMaxTeams;
		
	@FXML TextField wrLocation;
	@FXML TextField wrDate;
	@FXML TextField wrPtSpread;
	@FXML TextField wrLockPerc;
	@FXML Label	wrSliderPerc;
	@FXML TextField wrCustomScore;
	@FXML Slider wrSlider;
	@FXML Button wrReset;
	@FXML Button wrIndReset;
	@FXML Button wrLock;
	@FXML Button wrLockReset;
	
	@FXML CheckBox wrFlex;
	
	@FXML
	public void showWrDetails() {
		int index = wrTable.getSelectionModel().getSelectedIndex();
		WR wr = ListHelper.wrs.get(index);
		wrLocation.setText(wr.getMatchup().getHomeTeam().getMarket());
		wrDate.setText(wr.getMatchup().getDate());
		wrPtSpread.setText(wr.getMatchup().getPtSpread());
		wrSlider.setValue(0);
		wrCustomScore.setText("");
		wrSliderPerc.setText("");
		wrLockPerc.setDisable(false);
		wrLockPerc.setText(wr.getLockedPerc());
		if (WR.wrLockNum > 1) {
			if (wr.getLockedPerc().equals("")) {
				wrLockPerc.setText("MAX");
				wrLockPerc.setDisable(true);
			} else {
				wrLockPerc.setText(wr.getLockedPerc());
				wrLockPerc.setDisable(true);
			}
		}
	}
	
	@FXML
	public void wrLockPress() {
		int index = wrTable.getSelectionModel().getSelectedIndex();
		WR wr = ListHelper.wrs.get(index);
		wr.setLockedPerc(wrLockPerc.getText());
		WR.wrLockNum++;
		ListHelper.lockedPlayers.add(wr);
		wrTable.refresh();
	}
	
	@FXML
	public void wrLockResetPress() {
		int index = wrTable.getSelectionModel().getSelectedIndex();
		WR wr = ListHelper.wrs.get(index);
		if (!wr.getLockedPerc().equals("")) {
			wr.setLockedPerc("");
			wrLockPerc.setText("");
			if (WR.wrLockNum > 0) {
				WR.wrLockNum--;
				ListHelper.lockedPlayers.remove(wr);
			}
			if (wrLockPerc.isDisabled()) {
				wrLockPerc.setDisable(false);
			}
		}
		wrTable.refresh();
	}
	
	@FXML
	public void wrSlider() {
		DecimalFormat df = new DecimalFormat("#.0");
		wrSliderPerc.setText(df.format(wrSlider.getValue()) + "%");
		int index = wrTable.getSelectionModel().getSelectedIndex();
		if (index > -1) {
			WR wr = ListHelper.wrs.get(index);
			double newNum = wr.getProjection() * (1 + (wrSlider.getValue()/100));
			wr.setCustom(newNum);
			wr.setCustomized(true);
			wrCustomScore.setText(df.format(newNum));
			wrTable.refresh();
		}
	}
	
	@FXML
	public void wrIndResetButton() {
		int index = wrTable.getSelectionModel().getSelectedIndex();
		if (index > -1) {
			WR wr = ListHelper.wrs.get(index);
			wr.setCustom(wr.getProjection());
			wr.setCustomized(false);
			wrCustomScore.setText("");
			wrSlider.setValue(0);
			wrSliderPerc.setText("0%");
			wrTable.refresh();
		} 
	}
	
	@FXML
	public void wrDefaultButton() {
		wrMinProj.setText("5.5");
		wrMaxTeams.setText("25");
	}
	
	// TE TAB
	@FXML private TableView<TE> teTable;
	@FXML private TableColumn<TE, String> tePlayerName;
	@FXML private TableColumn<TE, String> tePlayerTeam;
	@FXML private TableColumn<TE, String> tePlayerOpp;
	@FXML private TableColumn<TE, String> teOverUnder;
	@FXML private TableColumn<TE, String> teProjRost;
	@FXML private TableColumn<TE, Integer> teSalary;
	@FXML private TableColumn<TE, Double> teProjPoints;
	@FXML private TableColumn<TE, Double> teCustProj;
	@FXML private TableColumn<TE, Boolean> teExclude;
	@FXML private TableColumn<TE, String> teLockedPercCol;
	
	@FXML TextField teMinProj;
	@FXML TextField teMaxTeams;
		
	@FXML TextField teLocation;
	@FXML TextField teDate;
	@FXML TextField tePtSpread;
	@FXML TextField teLockPerc;
	@FXML Label	teSliderPerc;
	@FXML TextField teCustomScore;
	@FXML Slider teSlider;
	@FXML Button teReset;
	@FXML Button teIndReset;
	@FXML Button teLock;
	@FXML Button teLockReset;
	
	@FXML CheckBox teFlex;
	
	@FXML
	public void showTeDetails() {
		int index = teTable.getSelectionModel().getSelectedIndex();
		TE te = ListHelper.tes.get(index);
		teLocation.setText(te.getMatchup().getHomeTeam().getMarket());
		teDate.setText(te.getMatchup().getDate());
		tePtSpread.setText(te.getMatchup().getPtSpread());
		teSlider.setValue(0);
		teCustomScore.setText("");
		teSliderPerc.setText("");
		teLockPerc.setDisable(false);
		teLockPerc.setText(te.getLockedPerc());
		if (TE.teLockNum > 0) {
			if (te.getLockedPerc().equals("")) {
				teLockPerc.setText("MAX");
				teLockPerc.setDisable(true);
			} else {
				teLockPerc.setText(te.getLockedPerc());
				teLockPerc.setDisable(true);
			}
		}
	}
	
	@FXML
	public void teLockPress() {
		int index = teTable.getSelectionModel().getSelectedIndex();
		TE te = ListHelper.tes.get(index);
		te.setLockedPerc(teLockPerc.getText());
		TE.teLockNum++;
		ListHelper.lockedPlayers.add(te);
		teTable.refresh();
	}
	
	@FXML
	public void teLockResetPress() {
		int index = teTable.getSelectionModel().getSelectedIndex();
		TE te = ListHelper.tes.get(index);
		if (!te.getLockedPerc().equals("")) {
			te.setLockedPerc("");
			teLockPerc.setText("");
			if (TE.teLockNum > 0) {
				TE.teLockNum--;
				ListHelper.lockedPlayers.remove(te);
			}
			if (teLockPerc.isDisabled()) {
				teLockPerc.setDisable(false);
			}
		}
		teTable.refresh();
	}
	
	@FXML
	public void teSlider() {
		DecimalFormat df = new DecimalFormat("#.0");
		teSliderPerc.setText(df.format(teSlider.getValue()) + "%");
		int index = teTable.getSelectionModel().getSelectedIndex();
		if (index > -1) {
			TE te = ListHelper.tes.get(index);
			double newNum = te.getProjection() * (1 + (teSlider.getValue()/100));
			te.setCustom(newNum);
			te.setCustomized(true);
			teCustomScore.setText(df.format(newNum));
			teTable.refresh();
		}
	}
	
	@FXML
	public void teIndResetButton() {
		int index = teTable.getSelectionModel().getSelectedIndex();
		if (index > -1) {
			TE te = ListHelper.tes.get(index);
			te.setCustom(te.getProjection());
			te.setCustomized(false);
			teCustomScore.setText("");
			teSlider.setValue(0);
			teSliderPerc.setText("0%");
			teTable.refresh();
		} 
	}
	
	@FXML
	public void teDefaultButton() {
		teMinProj.setText("5.5");
		teMaxTeams.setText("25");
	}
	
	// DST TAB
	@FXML private TableView<DST> dstTable;
	@FXML private TableColumn<DST, String> dstPlayerName;
	@FXML private TableColumn<DST, String> dstPlayerOpp;
	@FXML private TableColumn<DST, String> dstOverUnder;
	@FXML private TableColumn<DST, String> dstProjRost;
	@FXML private TableColumn<DST, Integer> dstSalary;
	@FXML private TableColumn<DST, Double> dstProjPoints;
	@FXML private TableColumn<DST, Double> dstCustProj;
	@FXML private TableColumn<DST, Boolean> dstExclude;
	@FXML private TableColumn<DST, String> dstLockedPercCol;
	
	@FXML TextField dstMinProj;
	@FXML TextField dstMaxTeams;
		
	@FXML TextField dstLocation;
	@FXML TextField dstDate;
	@FXML TextField dstPtSpread;
	@FXML TextField dstLockPerc;
	@FXML Label	dstSliderPerc;
	@FXML TextField dstCustomScore;
	@FXML Slider dstSlider;
	@FXML Button dstReset;
	@FXML Button dstIndReset;
	@FXML Button dstLock;
	@FXML Button dstLockReset;
	
	@FXML CheckBox dstNoConflict;
	
	@FXML
	public void showDstDetails() {
		int index = dstTable.getSelectionModel().getSelectedIndex();
		DST dst = ListHelper.dsts.get(index);
		dstLocation.setText(dst.getMatchup().getHomeTeam().getMarket());
		dstDate.setText(dst.getMatchup().getDate());
		dstPtSpread.setText(dst.getMatchup().getPtSpread());
		dstSlider.setValue(0);
		dstCustomScore.setText("");
		dstSliderPerc.setText("");
		dstLockPerc.setDisable(false);
		dstLockPerc.setText(dst.getLockedPerc());
		if (DST.dstLockNum > 0) {
			if (dst.getLockedPerc().equals("")) {
				dstLockPerc.setText("MAX");
				dstLockPerc.setDisable(true);
			} else {
				dstLockPerc.setText(dst.getLockedPerc());
				dstLockPerc.setDisable(true);
			}
		}
	}
	
	@FXML
	public void dstLockPress() {
		int index = dstTable.getSelectionModel().getSelectedIndex();
		DST dst = ListHelper.dsts.get(index);
		dst.setLockedPerc(dstLockPerc.getText());
		DST.dstLockNum++;
		ListHelper.lockedPlayers.add(dst);
		dstTable.refresh();
	}
	
	@FXML
	public void dstLockResetPress() {
		int index = dstTable.getSelectionModel().getSelectedIndex();
		DST dst = ListHelper.dsts.get(index);
		if (!dst.getLockedPerc().equals("")) {
			dst.setLockedPerc("");
			dstLockPerc.setText("");
			if (DST.dstLockNum > 0) {
				DST.dstLockNum--;
				ListHelper.lockedPlayers.remove(dst);
			}
			if (dstLockPerc.isDisabled()) {
				dstLockPerc.setDisable(false);
			}
		}
		dstTable.refresh();
	}
	
	@FXML
	public void dstSlider() {
		DecimalFormat df = new DecimalFormat("#.0");
		dstSliderPerc.setText(df.format(dstSlider.getValue()) + "%");
		int index = dstTable.getSelectionModel().getSelectedIndex();
		if (index > -1) {
			DST dst = ListHelper.dsts.get(index);
			double newNum = dst.getProjection() * (1 + (dstSlider.getValue()/100));
			dst.setCustom(newNum);
			dstCustomScore.setText(df.format(newNum));
			dstTable.refresh();
		}
	}
	
	@FXML
	public void dstIndResetButton() {
		int index = dstTable.getSelectionModel().getSelectedIndex();
		if (index > -1) {
			DST dst = ListHelper.dsts.get(index);
			dst.setCustom(dst.getProjection());
			dstCustomScore.setText("");
			dstSlider.setValue(0);
			dstSliderPerc.setText("0%");
			dstTable.refresh();
		} 
	}
	
	@FXML
	public void dstDefaultButton() {
		dstMinProj.setText("5.5");
		dstMaxTeams.setText("25");
	}
	
	
	// MATCHUP TAB
	@FXML private TableView<Matchup> matchupTable;
	@FXML private TableColumn<Matchup, String> matchupHomeTeam;
	@FXML private TableColumn<Matchup, String> matchupAwayTeam;
	@FXML private TableColumn<Matchup, String> matchupLocation;
	@FXML private TableColumn<Matchup, String> matchupDate;
	@FXML private TableColumn<Matchup, String> matchupOverUnder;
	@FXML private TableColumn<Matchup, String> matchupPointSpread;
	@FXML private TableColumn<Matchup, String> matchupHomeAdjustCol;
	@FXML private TableColumn<Matchup, String> matchupAwayAdjustCol;
	
	@FXML Button matchupHomeAdjust;
	@FXML Button matchupHomeReset;
	@FXML Button matchupAwayAdjust;
	@FXML Button matchupAwayReset;
	@FXML Slider matchupSlider;
	@FXML TextField matchupSliderPerc;
	@FXML Label matchupName;
	
	@FXML
	public void matchupInfo() {
		int index = matchupTable.getSelectionModel().getSelectedIndex();
		if (index > -1) {
			matchupName.setText(ListHelper.nflMatchups.get(index).toString());
		}
		matchupSlider.setValue(0);
		matchupSliderPerc.setText("0%");
		
	}
	
	@FXML
	public void matchupSlider() {
		DecimalFormat df = new DecimalFormat("#.0");
		matchupSliderPerc.setText(df.format(matchupSlider.getValue()) + "%");
	}
	
	@FXML
	public void matchupAwayAdj() {
		int index = matchupTable.getSelectionModel().getSelectedIndex();
		if (index > -1) {
			Matchup matchup = ListHelper.nflMatchups.get(index);
			Team awayTeam = matchup.getAwayTeam();
			Double sliderValue = matchupSlider.getValue();
			matchup.setAwayAdjust(Double.toString(sliderValue));
			
			for (RB rb : ListHelper.rbs) {
				if (rb.getPlayerTeam().equals(awayTeam) && !rb.isCustomized()) {
					rb.setCustom(rb.getProjection() * (1 + (sliderValue/100)));
				}
				
			}
			
			for (WR wr : ListHelper.wrs) {
				if (wr.getPlayerTeam().equals(awayTeam) && !wr.isCustomized()) {
					wr.setCustom(wr.getProjection() * (1 + (sliderValue/100)));
				}
			}
			
			for (TE te : ListHelper.tes) {
				if (te.getPlayerTeam().equals(awayTeam) && !te.isCustomized()) {
					te.setCustom(te.getProjection() * (1 + (sliderValue/100)));
				}
			}
			
			
			rbTable.refresh();
			wrTable.refresh();
			teTable.refresh();
			dstTable.refresh();
			matchupTable.refresh();
			
			matchupSlider.setValue(0);
			matchupSliderPerc.setText("0%");
		} 
		
	}
	
	@FXML
	public void matchupAwayRes() {
		int index = matchupTable.getSelectionModel().getSelectedIndex();
		if (index > -1) {
			Matchup matchup = ListHelper.nflMatchups.get(index);
			Team awayTeam = matchup.getAwayTeam();
			matchup.setAwayAdjust("");
						
			for (RB rb : ListHelper.rbs) {
				if (rb.getPlayerTeam().equals(awayTeam) && !rb.isCustomized()) {
					rb.setCustom(rb.getProjection());
				}
				
			}
			
			for (WR wr : ListHelper.wrs) {
				if (wr.getPlayerTeam().equals(awayTeam) && !wr.isCustomized()) {
					wr.setCustom(wr.getProjection());
				}
			}
			
			for (TE te : ListHelper.tes) {
				if (te.getPlayerTeam().equals(awayTeam) && !te.isCustomized()) {
					te.setCustom(te.getProjection());
				}
			}
			
			
			rbTable.refresh();
			wrTable.refresh();
			teTable.refresh();
			dstTable.refresh();
			matchupTable.refresh();
			
			matchupSlider.setValue(0);
			matchupSliderPerc.setText("0%");
		} 
		
	}
	
	@FXML
	public void matchupHomeAdj() {
		int index = matchupTable.getSelectionModel().getSelectedIndex();
		if (index > -1) {
			Matchup matchup = ListHelper.nflMatchups.get(index);
			Team homeTeam = matchup.getHomeTeam();
			Double sliderValue = matchupSlider.getValue();
			matchup.setHomeAdjust(Double.toString(sliderValue));
			
			for (RB rb : ListHelper.rbs) {
				if (rb.getPlayerTeam().equals(homeTeam) && !rb.isCustomized()) {
					rb.setCustom(rb.getProjection() * (1 + (sliderValue/100)));
				}
				
			}
			
			for (WR wr : ListHelper.wrs) {
				if (wr.getPlayerTeam().equals(homeTeam) && !wr.isCustomized()) {
					wr.setCustom(wr.getProjection() * (1 + (sliderValue/100)));
				}
			}
			
			for (TE te : ListHelper.tes) {
				if (te.getPlayerTeam().equals(homeTeam) && !te.isCustomized()) {
					te.setCustom(te.getProjection() * (1 + (sliderValue/100)));
				}
			}
			
			
			rbTable.refresh();
			wrTable.refresh();
			teTable.refresh();
			dstTable.refresh();
			matchupTable.refresh();
			
			matchupSlider.setValue(0);
			matchupSliderPerc.setText("0%");
		} 
		
	}
	
	@FXML
	public void matchupHomeRes() {
		int index = matchupTable.getSelectionModel().getSelectedIndex();
		if (index > -1) {
			Matchup matchup = ListHelper.nflMatchups.get(index);
			Team homeTeam = matchup.getHomeTeam();
			matchup.setHomeAdjust("");
						
			for (RB rb : ListHelper.rbs) {
				if (rb.getPlayerTeam().equals(homeTeam) && !rb.isCustomized()) {
					rb.setCustom(rb.getProjection());
				}
				
			}
			
			for (WR wr : ListHelper.wrs) {
				if (wr.getPlayerTeam().equals(homeTeam) && !wr.isCustomized()) {
					wr.setCustom(wr.getProjection());
				}
			}
			
			for (TE te : ListHelper.tes) {
				if (te.getPlayerTeam().equals(homeTeam) && !te.isCustomized()) {
					te.setCustom(te.getProjection());
				}
			}
			
			
			rbTable.refresh();
			wrTable.refresh();
			teTable.refresh();
			dstTable.refresh();
			matchupTable.refresh();
			
			matchupSlider.setValue(0);
			matchupSliderPerc.setText("0%");
		} 
		
	}
	
	// Lineups Tab
	@FXML private Tab lineupTab;
	@FXML private TableView<Lineup> lineupTable;
	@FXML private TableColumn<Lineup, QB> qbL;
	@FXML private TableColumn<Lineup, RB> rb1L;
	@FXML private TableColumn<Lineup, RB> rb2L;
	@FXML private TableColumn<Lineup, WR> wr1L;
	@FXML private TableColumn<Lineup, WR> wr2L;
	@FXML private TableColumn<Lineup, WR> wr3L;
	@FXML private TableColumn<Lineup, TE> teL;
	@FXML private TableColumn<Lineup, Offense> flexL;
	@FXML private TableColumn<Lineup, DST> dstL;
	@FXML private TableColumn<Lineup, Double> projL;
	@FXML private TableColumn<Lineup, Integer> salL;
	
	// method to set Lineup Helper statics 
	@FXML public void setLineupHelperAtts() {
		
		LineupHelper.lineupList.clear();
		lineupTable.setItems(getLineups(LineupHelper.lineupList));
		lineupTable.refresh();
		lineupTab.setDisable(true);
		saveExport.setDisable(true);
		
		// set number of iterations
		LineupHelper.numIterations = Integer.parseInt(numIterations.getText());
		
		//QB
		LineupHelper.qbF = lockedQB;
		
		//RB
		LineupHelper.rbMinScore = Double.parseDouble(rbMinProj.getText());
		LineupHelper.rbMaxTeams = Double.parseDouble(rbMaxTeams.getText());
		
		//WR
		LineupHelper.wrMinScore = Double.parseDouble(wrMinProj.getText());
		LineupHelper.wrMaxTeams = Double.parseDouble(wrMaxTeams.getText());
		
		//TE
		LineupHelper.teMinScore = Double.parseDouble(teMinProj.getText());
		LineupHelper.teMaxTeams = Double.parseDouble(teMaxTeams.getText());
		
		//DST
		LineupHelper.dstMinScore = Double.parseDouble(dstMinProj.getText());
		LineupHelper.dstMaxTeams = Double.parseDouble(dstMaxTeams.getText());
		
		//FLEX Booleans
		LineupHelper.rbFlexF = rbFlex.isSelected();
		LineupHelper.wrFlexF = wrFlex.isSelected();
		LineupHelper.teFlexF = teFlex.isSelected();
		
		//DST Conflict Boolean
		LineupHelper.dstConflict = dstNoConflict.isSelected();
		
		//set list lengths
		LineupHelper.rb1LineupsLength = Stack.getRb1Num();
		LineupHelper.rb2LineupsLength = Stack.getRb2Num();
		LineupHelper.wr1LineupsLength = Stack.getWr1Num();
		LineupHelper.wr2LineupsLength = Stack.getWr2Num();
		LineupHelper.wr3LineupsLength = Stack.getWr3Num();
		LineupHelper.teLineupsLength = Stack.getTeNum();
		
		setLocks();
		
		LineupHelper.generateLineups();		
		
		// enable tab lineup and reset the list
		lineupTab.setDisable(false);
		lineupTable.setItems(getLineups(LineupHelper.lineupList));
		lineupTable.refresh();
		
		saveExport.setDisable(false);
		
		
	}
	
	@FXML
	public void saveExportCSV () throws IOException {
		
		FileHelper.saveExportCSV();
		
		
	}

	
	//@FXML
	public int checkStacksLeft() {
		return maxStacks - 
				Stack.getRb1Num() - 
				Stack.getRb2Num() - 
				Stack.getTeNum() - 
				Stack.getWr1Num() - 
				Stack.getWr2Num() - 
				Stack.getWr3Num();
	}
	
	
	@FXML 
	public void showStackDetails() {
		rb1StackName.setText(Stack.getRb1().getName());
		rb2StackName.setText(Stack.getRb2().getName());
		teStackName.setText(Stack.getTe().getName());
		wr1StackName.setText(Stack.getWr1().getName());
		wr2StackName.setText(Stack.getWr2().getName());
		wr3StackName.setText(Stack.getWr3().getName());
		rb1StackNum.setText("");
		rb2StackNum.setText("");
		teStackNum.setText("");
		wr1StackNum.setText("");
		wr2StackNum.setText("");
		wr3StackNum.setText("");
		totNumStacks.setText("Total Teams: " + maxStacks);
		numStacksLeft.setText("Max Stacks Remaining: " + maxStacks);
				
		
		
		rb1StackNum.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
								
				try {
					  int temp = Integer.parseInt(rb1StackNum.getText());
					  Stack.setRb1Num(temp);
					  numStacksLeft.setText("Max Stacks Remaining: " + checkStacksLeft());
					  if (checkStacksLeft() < 0) {
						  Stack.setRb1Num(Stack.getRb1Num() + checkStacksLeft());
						  rb1StackNum.setText(Integer.toString(Stack.getRb1Num()));
						  numStacksLeft.setText("Max Stacks Remaining: " + checkStacksLeft());
					  }
					}
					catch(Exception e) {
					  Stack.setRb1Num(0);
					  rb1StackNum.setText("0");
					}
				
			}
		});
		
		rb2StackNum.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				try {
					  int temp = Integer.parseInt(rb2StackNum.getText());
					  Stack.setRb2Num(temp);
					  numStacksLeft.setText("Max Stacks Remaining: " + checkStacksLeft());
					  if (checkStacksLeft() < 0) {
						  Stack.setRb2Num(Stack.getRb2Num() + checkStacksLeft());
						  rb2StackNum.setText(Integer.toString(Stack.getRb2Num()));
						  numStacksLeft.setText("Max Stacks Remaining: " + checkStacksLeft());
					  }
					}
					catch(Exception e) {
					  Stack.setRb2Num(0);
					  rb2StackNum.setText("0");
					}
			}
		});
		
		teStackNum.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				try {
					  int temp = Integer.parseInt(teStackNum.getText());
					  Stack.setTeNum(temp);
					  numStacksLeft.setText("Max Stacks Remaining: " + checkStacksLeft());
					  if (checkStacksLeft() < 0) {
						  Stack.setTeNum(Stack.getTeNum() + checkStacksLeft());
						  teStackNum.setText(Integer.toString(Stack.getTeNum()));
						  numStacksLeft.setText("Max Stacks Remaining: " + checkStacksLeft());
					  }
					}
					catch(Exception e) {
					  Stack.setTeNum(0);
					  teStackNum.setText("0");
					}
			}
		});
		
		wr1StackNum.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				try {
					  int temp = Integer.parseInt(wr1StackNum.getText());
					  Stack.setWr1Num(temp);
					  numStacksLeft.setText("Max Stacks Remaining: " + checkStacksLeft());
					  if (checkStacksLeft() < 0) {
						  Stack.setWr1Num(Stack.getWr1Num() + checkStacksLeft());
						  wr1StackNum.setText(Integer.toString(Stack.getWr1Num()));
						  numStacksLeft.setText("Max Stacks Remaining: " + checkStacksLeft());
					  }
					}
					catch(Exception e) {
					  Stack.setWr1Num(0);
					  wr1StackNum.setText("0");
					}
			}
		});
		
		wr2StackNum.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				try {
					  int temp = Integer.parseInt(wr2StackNum.getText());
					  Stack.setWr2Num(temp);
					  numStacksLeft.setText("Max Stacks Remaining: " + checkStacksLeft());
					  if (checkStacksLeft() < 0) {
						  Stack.setWr2Num(Stack.getWr2Num() + checkStacksLeft());
						  wr2StackNum.setText(Integer.toString(Stack.getWr2Num()));
						  numStacksLeft.setText("Max Stacks Remaining: " + checkStacksLeft());
					  }
					}
					catch(Exception e) {
					  Stack.setWr2Num(0);
					  wr2StackNum.setText("0");
					}
			}
		});
		
		wr3StackNum.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				try {
					  int temp = Integer.parseInt(wr3StackNum.getText());
					  Stack.setWr3Num(temp);
					  numStacksLeft.setText("Max Stacks Remaining: " + checkStacksLeft());
					  if (checkStacksLeft() < 0) {
						  Stack.setWr3Num(Stack.getWr3Num() + checkStacksLeft());
						  wr3StackNum.setText(Integer.toString(Stack.getWr3Num()));
						  numStacksLeft.setText("Max Stacks Remaining: " + checkStacksLeft());
					  }
					}
					catch(Exception e) {
					  Stack.setWr3Num(0);
					  wr3StackNum.setText("0");
					}
			}
		});
	}
	
	
	
	@FXML
	public void qBButtonLockClick() {
		int index = qbTable.getSelectionModel().getSelectedIndex();
		if (index < 0) {
			return;
		}
		
		for (QB qb : ListHelper.qbs) {
			qb.setLocked(false);
		}
		
				
		ListHelper.qbs.get(index).setLocked(true);
		qbStacks.setText(ListHelper.qbs.get(index) + " STACKS");
		
		
		// if the stack list isn't empty, add these stacks back to the list
		if (Stack.getRb1() != null) {
			ListHelper.rbs.add(Stack.getRb1());
			ListHelper.rbs.add(Stack.getRb2());
			ListHelper.wrs.add(Stack.getWr1());
			ListHelper.wrs.add(Stack.getWr2());
			ListHelper.wrs.add(Stack.getWr3());
			ListHelper.tes.add(Stack.getTe());
			ListHelper.dsts.add(lockedQBOpponent);
		}
		
		stack = Stack.setStackPlayers(ListHelper.stacks);
		
		showStackDetails();
		
		ListHelper.rbs.remove(Stack.getRb1());
		ListHelper.rbs.remove(Stack.getRb2());
		ListHelper.wrs.remove(Stack.getWr1());
		ListHelper.wrs.remove(Stack.getWr2());
		ListHelper.wrs.remove(Stack.getWr3());
		ListHelper.tes.remove(Stack.getTe());
		
		
		Collections.sort(ListHelper.qbs);
		Collections.reverse(ListHelper.qbs);
		qbTable.setItems(getQBs(ListHelper.qbs));
		qbTable.refresh();
		Collections.sort(ListHelper.rbs);
		Collections.reverse(ListHelper.rbs);
		rbTable.setItems(getRBs(ListHelper.rbs));
		rbTable.refresh();
		Collections.sort(ListHelper.wrs);
		Collections.reverse(ListHelper.wrs);
		wrTable.setItems(getWRs(ListHelper.wrs));
		wrTable.refresh();
		Collections.sort(ListHelper.tes);
		Collections.reverse(ListHelper.tes);
		teTable.setItems(getTEs(ListHelper.tes));
		teTable.refresh();
		
		lockedQB = ListHelper.qbs.get(index);
		for (DST dst : ListHelper.dsts) {
			if (lockedQB.getOppTeam().equals(dst.getPlayerTeam())) {
				lockedQBOpponent = dst;
			}
		}
		
		ListHelper.dsts.remove(lockedQBOpponent);
		Collections.sort(ListHelper.dsts);
		Collections.reverse(ListHelper.dsts);
		dstTable.setItems(getDSTs(ListHelper.dsts));
		dstTable.refresh();
		
		
		lineupGenerator.setText("Generate Lineups");
		lineupGenerator.setDisable(false);
	}
	
	
	@FXML
	public void qBMouseClick() {
		
		int index = qbTable.getSelectionModel().getSelectedIndex();
		if (index < 0) {
			return;
		}
	
		showQbDetails();
		qbTable.refresh();
	}
	
	@FXML
	public void rBMouseClick() {
		
		int index = rbTable.getSelectionModel().getSelectedIndex();
		if (index < 0) {
			return;
		}
	
		showRbDetails();
		rbTable.refresh();
	}
	
	@FXML
	public void wRMouseClick() {
		
		int index = wrTable.getSelectionModel().getSelectedIndex();
		if (index < 0) {
			return;
		}
	
		showWrDetails();
		wrTable.refresh();
	}
	
	@FXML
	public void tEMouseClick() {
		
		int index = teTable.getSelectionModel().getSelectedIndex();
		if (index < 0) {
			return;
		}
	
		showTeDetails();
		teTable.refresh();
	}
	
	@FXML
	public void dsTMouseClick() {
		
		int index = dstTable.getSelectionModel().getSelectedIndex();
		if (index < 0) {
			return;
		}
	
		showDstDetails();
		dstTable.refresh();
	}
	
 
	/*
	 * Initialize Controller Class
	 */
	
	@Override
	public void initialize (URL url, ResourceBundle rb) {
		
		qbSetup();				
		rbSetup();
		wrSetup();
		teSetup();
		dstSetup();
		matchupSetup();
		lineupSetup();
		generatorSetup();
		
			
	}

	public void lineupSetup() {
		// set up the rows
		qbL.setCellValueFactory(new PropertyValueFactory<Lineup, QB>("qb"));
		rb1L.setCellValueFactory(new PropertyValueFactory<Lineup, RB>("rb1"));
		rb2L.setCellValueFactory(new PropertyValueFactory<Lineup, RB>("rb2"));
		wr1L.setCellValueFactory(new PropertyValueFactory<Lineup, WR>("wr1"));
		wr2L.setCellValueFactory(new PropertyValueFactory<Lineup, WR>("wr2"));
		wr3L.setCellValueFactory(new PropertyValueFactory<Lineup, WR>("wr3"));
		teL.setCellValueFactory(new PropertyValueFactory<Lineup, TE>("te"));
		flexL.setCellValueFactory(new PropertyValueFactory<Lineup, Offense>("flex"));
		dstL.setCellValueFactory(new PropertyValueFactory<Lineup, DST>("dst"));
		projL.setCellValueFactory(new PropertyValueFactory<Lineup, Double>("lineupProj"));
		
		qbL.setSortable(false);
		rb1L.setSortable(false);
		rb2L.setSortable(false);
		wr1L.setSortable(false);
		wr2L.setSortable(false);
		wr3L.setSortable(false);
		teL.setSortable(false);
		flexL.setSortable(false);
		dstL.setSortable(false);
		projL.setSortable(false);
		
		// set lineup table 
		lineupTable.setItems(getLineups(LineupHelper.lineupList));
		
	}

	
	public void qbSetup() {
		
		// set up the rows
		qbPlayerName.setCellValueFactory(new PropertyValueFactory<QB, String>("name"));
		qbPlayerTeam.setCellValueFactory(new PropertyValueFactory<QB, String>("playerTeam"));
		qbPlayerOpp.setCellValueFactory(new PropertyValueFactory<QB, String>("oppTeam"));
		qbOverUnder.setCellValueFactory(new PropertyValueFactory<QB, String>("overUnder"));
		qbProjRost.setCellValueFactory(new PropertyValueFactory<QB, String>("estStart"));
		qbSalary.setCellValueFactory(new PropertyValueFactory<QB, Integer>("salary"));
		qbProjPoints.setCellValueFactory(new PropertyValueFactory<QB, Double>("projection"));
		qbCustProj.setCellValueFactory(new PropertyValueFactory<QB, Double>("custom"));
		
		
				
		// set table items
		qbTable.setItems(getQBs(ListHelper.qbs));
		
		qbPlayerName.setSortable(false);
		qbPlayerTeam.setSortable(false);
		qbPlayerOpp.setSortable(false);
		qbOverUnder.setSortable(false);
		qbProjRost.setSortable(false);
		qbSalary.setSortable(false);
		qbProjPoints.setSortable(false);
		qbCustProj.setSortable(false);
		
		
		// set up the rows for the stacks tableview
		stackName.setCellValueFactory(new PropertyValueFactory<Offense, String>("name"));
		stackPos.setCellValueFactory(new PropertyValueFactory<Offense, String>("pos"));
		stackProj.setCellValueFactory(new PropertyValueFactory<Offense, Double>("projection"));
		
	}
	
	
	// method to setup rb table
	public void rbSetup() {
		
		// set up the rows
		rbPlayerName.setCellValueFactory(new PropertyValueFactory<RB, String>("name"));
		rbPlayerTeam.setCellValueFactory(new PropertyValueFactory<RB, String>("PlayerTeam"));
		rbPlayerOpp.setCellValueFactory(new PropertyValueFactory<RB, String>("oppTeam"));
		rbOverUnder.setCellValueFactory(new PropertyValueFactory<RB, String>("overUnder"));
		rbProjRost.setCellValueFactory(new PropertyValueFactory<RB, String>("estStart"));
		rbSalary.setCellValueFactory(new PropertyValueFactory<RB, Integer>("salary"));
		rbProjPoints.setCellValueFactory(new PropertyValueFactory<RB, Double>("projection"));
		rbCustProj.setCellValueFactory(new PropertyValueFactory<RB, Double>("custom"));
		rbLockedPercCol.setCellValueFactory(new PropertyValueFactory<RB, String>("lockedPerc"));
		
		
				
		// set table items
		rbTable.setItems(getRBs(ListHelper.rbs));
		
		rbPlayerName.setSortable(false);
		rbPlayerTeam.setSortable(false);
		rbPlayerOpp.setSortable(false);
		rbOverUnder.setSortable(false);
		rbProjRost.setSortable(false);
		rbSalary.setSortable(false);
		rbProjPoints.setSortable(false);
		rbCustProj.setSortable(false);
		rbLockedPercCol.setSortable(false);
		
		rbLockPerc.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
								
				try {
					  double temp = Double.parseDouble(rbLockPerc.getText());
					  if (temp < 0.0) {
						  rbLockPerc.setText("0.0");
					  } 
					  
					  if (temp > 100.0) {
						  rbLockPerc.setText("100.0");
					  }
				}
					catch(Exception e) {
					  rbLockPerc.setText("");
					}
				
			}
		});
		
		
		rbMinProj.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
								
				try {
					  double temp = Double.parseDouble(rbMinProj.getText());
					  if (temp < 0.0) {
						  rbMinProj.setText("0.0");
					  }
					  if (temp > 15.0) {
						  rbMinProj.setText("15.0");
					  }
					  
					}
					catch(Exception e) {
					  rbMinProj.setText("5.5");
					}
				
			}
		});
		
		rbMaxTeams.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
								
				try {
					  double temp = Double.parseDouble(rbMaxTeams.getText());
					  if (temp < 0.0) {
						  rbMaxTeams.setText("0.0");
					  }
					  else if (temp > 100.0) {
						  rbMaxTeams.setText("100.0");
					  }
					  else {
						  rbMaxTeams.setText(rbMaxTeams.getText());
					  }
					  
					}
					catch(Exception e) {
						rbMaxTeams.setText("25.0");
					}
				
			}
		});
		
	
	}
	
	// method to setup wr table
	public void wrSetup() {
		
		// set up the rows
		wrPlayerName.setCellValueFactory(new PropertyValueFactory<WR, String>("name"));
		wrPlayerTeam.setCellValueFactory(new PropertyValueFactory<WR, String>("playerTeam"));
		wrPlayerOpp.setCellValueFactory(new PropertyValueFactory<WR, String>("oppTeam"));
		wrOverUnder.setCellValueFactory(new PropertyValueFactory<WR, String>("overUnder"));
		wrProjRost.setCellValueFactory(new PropertyValueFactory<WR, String>("estStart"));
		wrSalary.setCellValueFactory(new PropertyValueFactory<WR, Integer>("salary"));
		wrProjPoints.setCellValueFactory(new PropertyValueFactory<WR, Double>("projection"));
		wrCustProj.setCellValueFactory(new PropertyValueFactory<WR, Double>("custom"));
		wrLockedPercCol.setCellValueFactory(new PropertyValueFactory<WR, String>("lockedPerc"));
		
		
				
		// set table items
		wrTable.setItems(getWRs(ListHelper.wrs));
		
		wrPlayerName.setSortable(false);
		wrPlayerTeam.setSortable(false);
		wrPlayerOpp.setSortable(false);
		wrOverUnder.setSortable(false);
		wrProjRost.setSortable(false);
		wrSalary.setSortable(false);
		wrProjPoints.setSortable(false);
		wrCustProj.setSortable(false);
		wrLockedPercCol.setSortable(false);
		
		wrMinProj.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
								
				try {
					  double temp = Double.parseDouble(wrMinProj.getText());
					  if (temp < 0.0) {
						  wrMinProj.setText("0.0");
					  }
					  if (temp > 15.0) {
						  wrMinProj.setText("15.0");
					  }
					  
					}
					catch(Exception e) {
					  wrMinProj.setText("5.5");
					}
				
			}
		});
		
		wrMaxTeams.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
								
				try {
					  double temp = Double.parseDouble(wrMaxTeams.getText());
					  if (temp < 0) {
						  wrMaxTeams.setText("0.0");
					  }
					  else if (temp > 100) {
						  wrMaxTeams.setText("100.0");
					  }
					  else {
						  wrMaxTeams.setText(wrMaxTeams.getText());
					  }
					  
					}
					catch(Exception e) {
						wrMaxTeams.setText("25.0");
					}
				
			}
		});
		
	
	}
	
	// method to setup te table
	public void teSetup() {
		
		// set up the rows
		tePlayerName.setCellValueFactory(new PropertyValueFactory<TE, String>("name"));
		tePlayerTeam.setCellValueFactory(new PropertyValueFactory<TE, String>("playerTeam"));
		tePlayerOpp.setCellValueFactory(new PropertyValueFactory<TE, String>("oppTeam"));
		teOverUnder.setCellValueFactory(new PropertyValueFactory<TE, String>("overUnder"));
		teProjRost.setCellValueFactory(new PropertyValueFactory<TE, String>("estStart"));
		teSalary.setCellValueFactory(new PropertyValueFactory<TE, Integer>("salary"));
		teProjPoints.setCellValueFactory(new PropertyValueFactory<TE, Double>("projection"));
		teCustProj.setCellValueFactory(new PropertyValueFactory<TE, Double>("custom"));
		teLockedPercCol.setCellValueFactory(new PropertyValueFactory<TE, String>("lockedPerc"));
		
		
				
		// set table items
		teTable.setItems(getTEs(ListHelper.tes));
		
		tePlayerName.setSortable(false);
		tePlayerTeam.setSortable(false);
		tePlayerOpp.setSortable(false);
		teOverUnder.setSortable(false);
		teProjRost.setSortable(false);
		teSalary.setSortable(false);
		teProjPoints.setSortable(false);
		teCustProj.setSortable(false);
		teLockedPercCol.setSortable(false);
		
		teMinProj.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
								
				try {
					  double temp = Double.parseDouble(teMinProj.getText());
					  if (temp < 0.0) {
						  teMinProj.setText("0.0");
					  }
					  if (temp > 15.0) {
						  teMinProj.setText("15.0");
					  }
					  
					}
					catch(Exception e) {
					  teMinProj.setText("5.5");
					}
				
			}
		});
		
		teMaxTeams.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
								
				try {
					  double temp = Double.parseDouble(teMaxTeams.getText());
					  if (temp < 0) {
						  teMaxTeams.setText("0.0");
					  }
					  else if (temp > 100) {
						  teMaxTeams.setText("100.0");
					  }
					  else {
						  teMaxTeams.setText(teMaxTeams.getText());
					  }
					  
					}
					catch(Exception e) {
						teMaxTeams.setText("25.0");
					}
				
			}
		});
	
	}
	
	// method to setup dsts table
	public void dstSetup() {
		
		// set up the rows
		dstPlayerName.setCellValueFactory(new PropertyValueFactory<DST, String>("name"));
		dstPlayerOpp.setCellValueFactory(new PropertyValueFactory<DST, String>("oppTeam"));
		dstOverUnder.setCellValueFactory(new PropertyValueFactory<DST, String>("overUnder"));
		dstProjRost.setCellValueFactory(new PropertyValueFactory<DST, String>("estStart"));
		dstSalary.setCellValueFactory(new PropertyValueFactory<DST, Integer>("salary"));
		dstProjPoints.setCellValueFactory(new PropertyValueFactory<DST, Double>("projection"));
		dstCustProj.setCellValueFactory(new PropertyValueFactory<DST, Double>("custom"));
		dstLockedPercCol.setCellValueFactory(new PropertyValueFactory<DST, String>("lockedPerc"));
		
				
		// set table items
		dstTable.setItems(getDSTs(ListHelper.dsts));
		
		dstPlayerName.setSortable(false);
		dstPlayerOpp.setSortable(false);
		dstOverUnder.setSortable(false);
		dstProjRost.setSortable(false);
		dstSalary.setSortable(false);
		dstProjPoints.setSortable(false);
		dstCustProj.setSortable(false);
		dstLockedPercCol.setSortable(false);
		
		dstMinProj.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
								
				try {
					  double temp = Double.parseDouble(dstMinProj.getText());
					  if (temp < 0.0) {
						  dstMinProj.setText("0.0");
					  }
					  if (temp > 15.0) {
						  dstMinProj.setText("15.0");
					  }
					  
					}
					catch(Exception e) {
					  dstMinProj.setText("5.5");
					}
				
			}
		});
		
		
		dstMaxTeams.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
								
				try {
					  double temp = Double.parseDouble(dstMaxTeams.getText());
					  if (temp < 0) {
						  dstMaxTeams.setText("0.0");
					  }
					  else if (temp > 100) {
						  dstMaxTeams.setText("100.0");
					  }
					  else {
						  dstMaxTeams.setText(dstMaxTeams.getText());
					  }
					  
					}
					catch(Exception e) {
						dstMaxTeams.setText("25.0");
					}
				
			}
		});
		
	
	}
	
	// method to setup matchups table
	public void matchupSetup() {
		
		// set up the rows
		matchupHomeTeam.setCellValueFactory(new PropertyValueFactory<Matchup, String>("homeTeam"));
		matchupAwayTeam.setCellValueFactory(new PropertyValueFactory<Matchup, String>("awayTeam"));
		matchupLocation.setCellValueFactory(new PropertyValueFactory<Matchup, String>("location"));
		matchupDate.setCellValueFactory(new PropertyValueFactory<Matchup, String>("date"));
		matchupOverUnder.setCellValueFactory(new PropertyValueFactory<Matchup, String>("overUnder"));
		matchupPointSpread.setCellValueFactory(new PropertyValueFactory<Matchup, String>("ptSpread"));
		matchupHomeAdjustCol.setCellValueFactory(new PropertyValueFactory<Matchup, String>("homeAdjust"));
		matchupAwayAdjustCol.setCellValueFactory(new PropertyValueFactory<Matchup, String>("awayAdjust"));
		
						
		// set table items
		matchupTable.setItems(getMatchups(ListHelper.nflMatchups));
		
		matchupHomeTeam.setSortable(false);
		matchupAwayTeam.setSortable(false);
		matchupLocation.setSortable(false);
		matchupDate.setSortable(false);
		matchupOverUnder.setSortable(false);
		matchupPointSpread.setSortable(false);
		matchupHomeAdjustCol.setSortable(false);
		matchupAwayAdjustCol.setSortable(false);
		
		
	}
	
	// method to setup lineupgenerator
	public void generatorSetup() {
		
		numIterations.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
								
				try {
					  double temp = Double.parseDouble(numIterations.getText());
					  if (temp < 100000) {
						  numIterations.setText("100000");
					  }
					  else if (temp > 10000000) {
						  numIterations.setText("10000000");
					  }
					  else {
						  numIterations.setText(numIterations.getText());
					  }
					  
					}
					catch(Exception e) {
						numIterations.setText("1000000");
					}
				
			}
		});
		
	}
	

	
	// method to set locked players % percentages in the LineupHelper Static variables
	public void setLocks () {
		Collections.sort(ListHelper.lockedPlayers);
		Collections.reverse(ListHelper.lockedPlayers);
		
		for (Player player : ListHelper.lockedPlayers) {
			System.out.println(player);
		}
		
		// reset the static variables in case they were previously set
		LineupHelper.rbLock = null;
		LineupHelper.rbLockSize = 0.0;
		LineupHelper.wr1Lock = null;
		LineupHelper.wr1LockSize = 0.0;
		LineupHelper.wr2Lock = null;
		LineupHelper.wr2LockSize = 0.0;
		LineupHelper.teLock = null;
		LineupHelper.teLockSize = 0.0;
		LineupHelper.dstLock = null;
		LineupHelper.dstLockSize = 0.0;

		
		for (Player player : ListHelper.lockedPlayers) {
			if (player instanceof RB) {
				LineupHelper.rbLock = (RB) player;
				LineupHelper.rbLockSize = Double.parseDouble(player.getLockedPerc()); 
				
			} 
			else if (player instanceof TE) {
				LineupHelper.teLock = (TE) player;
				LineupHelper.teLockSize = Double.parseDouble(player.getLockedPerc());
				
			}
			else if (player instanceof DST) {
				LineupHelper.dstLock = (DST) player;
				LineupHelper.dstLockSize = Double.parseDouble(player.getLockedPerc());
			} 
			else {
				if (LineupHelper.wr1Lock == null) {
					LineupHelper.wr1Lock = (WR) player;
					LineupHelper.wr1LockSize = Double.parseDouble(player.getLockedPerc());
					
				} else {
					LineupHelper.wr2Lock = (WR) player;
					LineupHelper.wr2LockSize = Double.parseDouble(player.getLockedPerc());
				}
			}
		}
		
	}
	
	public ObservableList<Offense> getStacks(ArrayList<Offense> offList){
		ObservableList<Offense> offense = FXCollections.observableArrayList();
		
		for (Offense player : offList) {
			offense.add(player);
		}
		return offense;
	}
	
	
	public ObservableList<QB> getQBs(ArrayList<QB> qbList){
		
		ObservableList<QB> qbs = FXCollections.observableArrayList();
		
		for (QB qb : qbList) {
			qbs.add(qb);
		}
		
		return qbs;
		
		
	}
	
	public ObservableList<RB> getRBs(ArrayList<RB> rbList){
		
		ObservableList<RB> rbs = FXCollections.observableArrayList();
		
		for (RB rb : rbList) {
			rbs.add(rb);	
		}
		
		return rbs;
		
		
	}
	
	public ObservableList<WR> getWRs(ArrayList<WR> wrList){
		
		ObservableList<WR> wrs = FXCollections.observableArrayList();
		
		for (WR wr : wrList) {
			wrs.add(wr);
		}
		
		return wrs;
		
		
	}
	
	public ObservableList<TE> getTEs(ArrayList<TE> teList){
		
		ObservableList<TE> tes = FXCollections.observableArrayList();
		
		for (TE te : teList) {
			tes.add(te);
			
		}
		return tes;
		
		
	}
	
	public ObservableList<DST> getDSTs(ArrayList<DST> dstList){
		
		ObservableList<DST> dsts = FXCollections.observableArrayList();
		
		for (DST dst : dstList) {
			dsts.add(dst);
		}
		
		return dsts;
		
		
	}

	public ObservableList<Matchup> getMatchups(ArrayList<Matchup> matchupList){
		
		ObservableList<Matchup> matchups = FXCollections.observableArrayList();
		
		for (Matchup matchup : matchupList) {
			matchups.add(matchup);
		}
		
		return matchups;
	}
	
	public ObservableList<Lineup> getLineups(ArrayList<Lineup> lineupList) {
		
		ObservableList<Lineup> lineups = FXCollections.observableArrayList();
		
		for (Lineup lineup : lineupList) {
			lineups.add(lineup);
		}
		
		return lineups;
		
	}
	
	
}
