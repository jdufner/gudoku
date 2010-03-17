#!/usr/bin/perl

use strict;

my $raetselLang;
my $raetselKurz;
my $id;
my $level;

sub READFILE {
    # Dateiname aus Übergabeparameter holen
    my $filename = $_[0];
    #print($filename."\n");
    open(FILE, $filename);
    my $filecontent = join("", <FILE>);
    #print($filecontent."\n");
    close(FILE);
    return $filecontent
}

sub PRINTHTML {
    print("<option value=\"$raetselKurz\">$id $level</option>\n");
}

sub MAIN {
    foreach my $filename (<*.txt>) {
        #print ($filename."\n");
        my $filecontent = READFILE($filename);
        if ($filecontent =~ /Rätsel:\s+([0-9,\-]+)/) {
            $raetselLang = $1;
            #print($raetselLang."\n");
            $raetselKurz = $raetselLang;
            $raetselKurz =~ s/1-2-3-4-5-6-7-8-9/\./g;
            $raetselKurz =~ s/,//g;
            #print($raetselKurz."\n");
        }
        if ($filecontent =~ /ENDE\s+Sudoku\s+(\d+)/) {
            $id = $1;
            #print($id."\n");
        }
        if ($filecontent =~ /Schwierigkeitsgrad:\s+(\w+)/) {
            $level = $1;
            #print($level."\n");
        }
        PRINTHTML();
    }
}

MAIN();
