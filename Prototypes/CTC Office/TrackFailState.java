/*
 * The different kinds of failures a track segment can have
 */

public enum TrackFailState{
  FS_NORMAL,
  FS_TRACK_CIRCUIT_FAILURE,
  FS_BROKEN_RAIL,
  FS_POWER_FAILURE,
}
