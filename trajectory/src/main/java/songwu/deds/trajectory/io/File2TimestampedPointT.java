package songwu.deds.trajectory.io;

import songwu.deds.trajectory.data.TimeStampedPoint;
import songwu.deds.trajectory.data.TimeStampedPointT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

public class File2TimestampedPointT{
    protected int Traj_id = Integer.MIN_VALUE;
    protected int Longitude = Integer.MIN_VALUE;
    protected int Latitude = Integer.MIN_VALUE;
    protected int X = Integer.MIN_VALUE;
    protected int Y = Integer.MIN_VALUE;
    protected int Timestamp = Integer.MIN_VALUE;

    protected String file_path;
    protected String splitter;
    protected boolean with_header;

    public File2TimestampedPointT filePath(String file_path) {
        this.file_path = file_path;
        return this;
    }

    public File2TimestampedPointT splitter(String splitter) {
        this.splitter = splitter;
        return this;
    }

    public File2TimestampedPointT withHeader(boolean with_header) {
        this.with_header = with_header;
        return this;
    }

    public File2TimestampedPointT longitude(int index) {
        Longitude = index;
        return this;
    }

    public File2TimestampedPointT latitude(int index) {
        Latitude = index;
        return this;
    }

    public File2TimestampedPointT x(int index) {
        X = index;
        return this;
    }

    public File2TimestampedPointT y(int index) {
        Y = index;
        return this;
    }

    public File2TimestampedPointT trajId(int index) {
        Traj_id = index;
        return this;
    }

    public File2TimestampedPointT timestamp(int index) {
        Timestamp = index;
        return this;
    }

    public TimeStampedPoint modify(TimeStampedPoint point, String[] parts) throws ParseException {
        point.setX(Double.parseDouble(parts[X]));
        point.setY(Double.parseDouble(parts[Y]));
        point.latitide(Double.parseDouble(parts[Latitude]));
        point.longitude(Double.parseDouble(parts[Longitude]));
        point.timestamp_string(parts[Timestamp]);
        return point;
    }

    public List<TimeStampedPointT> go() throws IOException, ParseException {
        List<String> lines = Files.readAllLines(Paths.get(file_path));

        List<TimeStampedPointT> trajectories = new ArrayList<>();

        String previous_traj = "";

        for (String line : lines.subList(with_header ? 1 : 0, lines.size())) {
            String[] parts = line.split(splitter);
            if (!parts[Traj_id].equals(previous_traj)) {
                trajectories.add(new TimeStampedPointT(parts[Traj_id]));
                previous_traj = parts[Traj_id];
            }

            TimeStampedPoint point = new TimeStampedPoint();
            modify(point, parts);
            trajectories.get(trajectories.size() - 1).addPoint(point);
        }

        return trajectories;
    }

    public static void main(String[] args) throws IOException, ParseException {

    }
}

