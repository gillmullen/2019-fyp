declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define i32 @main ()
{
%.p.i = alloca i32

store i32 0, i32* %.p.i
br label %label1
label1: %.t1 = load i32, i32* %.p.i
%.t2 = icmp sle i32 %.t1, 3
br i1 %.t2, label %label2, label %label3
label2:
%.t3 = load i32, i32* %.p.i

call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 %.t3)
%.t5 = load i32, i32* %.p.i
%.t4 = add i32 %.t5, 1

store i32 %.t4, i32* %.p.i
br label %label1
label3:
ret i32 0
}
