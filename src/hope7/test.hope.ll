declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define i32 @main ()
{
%.t1 = icmp eq i32 3, 3
br i1 %.t1, label %label1, label %label2
label1:
call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32  0, i32 0), i32 2)
br label %label3
label2:
%.t2 = add i1 0, 0
br label %label3
label3:
call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32  0, i32 0), i32 3)
ret i32 0
}
