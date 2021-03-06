package songwu.deds.trajectory.data;

import songwu.deds.trajectory.utility.MyDate;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;

public class CriticalPointInterval {
    private String id;
    private String rank;
    private String type;
    private String startTime;
    private String endTime;

    public static double avgDuration(List<CriticalPointInterval> intervals) throws ParseException {
        if(intervals.size() == 0)
            return 0;
        double hours = 0;
        for(CriticalPointInterval interval : intervals)
            hours += MyDate.timestampDiff(interval.getEndTime(),
                    interval.getStartTime()) / 3600.0;
        return hours / intervals.size();
    }

    public static double spanDuration(List<CriticalPointInterval> intervals) throws ParseException {
        if(intervals.size() == 0)
            return 0;
        return MyDate.timestampDiff(intervals.get(intervals.size()-1).getEndTime(),
                intervals.get(0).getStartTime()) / 3600.0;
    }

    public static double totalDuration(List<CriticalPointInterval> intervals) throws ParseException {
        if(intervals.size() == 0)
            return 0;
        double hours = 0;
        for(CriticalPointInterval interval : intervals)
            hours += MyDate.timestampDiff(interval.getEndTime(),
                    interval.getStartTime()) / 3600.0;
        return hours;
    }

    public static double avgInBetweenDuration(List<CriticalPointInterval> intervals) throws ParseException {
        if(intervals.size() <= 1)
            return 0;
        return (spanDuration(intervals) - totalDuration(intervals)) / (intervals.size() - 1);
    }

    public double totalInBetweenDuration(List<CriticalPointInterval> intervals) throws ParseException {
        return (spanDuration(intervals) - totalDuration(intervals));
    }

    public String getRank() {
        return rank;
    }

    public CriticalPointInterval setRank(String rank) {
        this.rank = rank; return this;
    }

    public String getId(){
        return id;
    }

    public CriticalPointInterval setId(String id){
        this.id = id; return this;
    }

    public String getType() {
        return type;
    }

    public CriticalPointInterval setType(String type) {
        this.type = type; return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public CriticalPointInterval setStartTime(String startTime) {
        this.startTime = startTime; return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public CriticalPointInterval setEndTime(String endTime) {
        this.endTime = endTime; return this;
    }

    public static void saveIntervals(String outputPath, List<CriticalPointInterval> intervals) throws FileNotFoundException {
        try(PrintWriter writerInterval = new PrintWriter(outputPath)){
            writerInterval.write("rank,type,startTime,endTime,id\n");
            for(CriticalPointInterval interval : intervals){
                writerInterval.write(interval.getRank() + "," + interval.getType() + "," + interval.getStartTime() + "," + interval.getEndTime() + "," + interval.getId() + "\n");
            }
        }

    }
}
