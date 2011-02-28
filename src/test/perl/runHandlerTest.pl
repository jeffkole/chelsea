#!/usr/bin/perl

use Net::HandlerSocket;

my $args = { host => 'localhost', port => 9998 };
my $hs = new Net::HandlerSocket($args);

my $res = $hs->open_index(1, 'test', 'hs_test_table', 'PRIMARY', 'id,name,birthday');
die $hs->get_error() if $res->[0] != 0;

my $res = $hs->execute_single(1, '=', [ '1' ], 1, 0);
die $hs->get_error() if $res->[0] != 0;
shift(@$res);
print "id: '$res->[0]'\nname: '$res->[1]'\nbirthday: '$res->[2]'\n";

exit;

my $res = $hs->open_index(2, 'test', 'hs_test_table', 'hs_test_bday_idx', 'name,birthday');
die $hs->get_error() if $res->[0] != 0;

my $res = $hs->execute_single(2, '=', [ '1932-02-27' ], 10, 0);
die $hs->get_error() if $res->[0] != 0;
shift(@$res);
my $rows = 2; # I hope
for (my $row = 0; $row < $rows; ++$row) {
  print "name: '$res->[$row * $rows + 0]', birthday: '$res->[$row * $rows + 1]'\n";
}

my $res = $hs->execute_single(2, '>', [ '1950-01-01' ], 10, 0);
die $hs->get_error() if $res->[0] != 0;
shift(@$res);
my $rows = 2; # I hope
for (my $row = 0; $row < $rows; ++$row) {
  print "name: '$res->[$row * $rows + 0]', birthday: '$res->[$row * $rows + 1]'\n";
}
