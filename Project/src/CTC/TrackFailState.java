/*
 * The different kinds of failures a track segment can have
 */

public enum TrackFailState{
  //Normal and the other 3 failures are exclusive
  FS_NORMAL,
  FS_TRACK_CIRCUIT_FAILURE,
  FS_BROKEN_RAIL,
  FS_POWER_FAILURE,

  //More than 1 failure at a time
  FS_CIRCUIT_AND_RAIL,
  FS_CIRCUIT_AND_POWER,
  FS_RAIL_AND_POWER,

  //All 3 failures at a time
  FS_CIRCUIT_RAIL_POWER,
}
